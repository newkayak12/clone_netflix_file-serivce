package com.netflix_clone.fileservice.component.configure.feign;

import com.netflix_clone.fileservice.component.configure.ConfigMsg;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * Created on 2023-05-12
 * Project user-service
 */
@Configuration(value = "feignConfig")
@EnableFeignClients
public class FeignConfig {
    public FeignConfig() {
        ConfigMsg.msg("Feign");
    }
}
