package com.netflix_clone.fileservice.component.configure.redis;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.netflix_clone.fileservice.component.configure.ConfigMsg;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration(value = "redis_cache_configuration")
@EnableCaching
public class Config {

    private ObjectMapper objectMapper;
    public Config() {
        ConfigMsg.msg("Redis");
        this.objectMapper = new ObjectMapper();
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.objectMapper.registerModules(new JavaTimeModule(), new Jdk8Module());
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory();
        return lettuceConnectionFactory;
    }

    @Primary
    @Bean(name = "cacheManager")
    public RedisCacheManager cacheManager(RedisConnectionFactory cf, ResourceLoader rl) {   // 기본 캐시매니저(객체 통째로 보관)

        RedisCacheManager.RedisCacheManagerBuilder builder= RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(cf);
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig(rl.getClassLoader())
                .disableCachingNullValues()       // 널값 금지(캐싱시 unless("#result == null") 필수.)
                .entryTtl(Duration.ofDays(1))     // 기본 캐시 1일 유지.
                .computePrefixWith(CacheKeyPrefix.simple())       // name::key 처럼 key앞에 '::'를 삽입(redis-cli에서 get "name::key" 로 조회.)
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()));

        // 캐시별로 유효시간 다르게 정하기.
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();

        // 3초
        Duration d3s= Duration.ofSeconds(3);
        cacheConfigurations.put("pageHot100Talk", configuration.entryTtl(d3s));

        // 10분
        Duration d10m= Duration.ofMinutes(10);
        cacheConfigurations.put("listKeyword", configuration.entryTtl(d10m));

        // 1시간
        Duration d1h= Duration.ofHours(1);
        cacheConfigurations.put("listExMain", configuration.entryTtl(d1h));

        return builder.cacheDefaults(configuration).withInitialCacheConfigurations(cacheConfigurations).build();
    }

    private Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer() {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer =
                new Jackson2JsonRedisSerializer<>(Object.class);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        return jackson2JsonRedisSerializer;
    }

    //************************
    // generic json용 캐시매니저
    //************************
    @Bean(name = "gsonCacheManager")
    public RedisCacheManager gsonCacheManager(RedisConnectionFactory cf, ResourceLoader rl) {  // json으로 값 보관(detache가 필요한 entity들- 회원정보, ... 등)

        RedisCacheManager.RedisCacheManagerBuilder builder= RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(cf);
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig(rl.getClassLoader())
                .disableCachingNullValues()
                .entryTtl(Duration.ofDays(1))     // 기본 캐시 1일 유지.
                .computePrefixWith(CacheKeyPrefix.simple())
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer()));     //  json형식으로 value 저장.

        // 캐시별로 유효시간 다르게 정하기.
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();

        // 1시간
        Duration d1h= Duration.ofHours(1);
        cacheConfigurations.put("listCategory", configuration.entryTtl(d1h));

        return builder.cacheDefaults(configuration).withInitialCacheConfigurations(cacheConfigurations).build();
    }
}
