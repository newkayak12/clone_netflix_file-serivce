package com.netflix_clone.fileservice.component.configure.queryDsl;

import com.netflix_clone.fileservice.component.configure.ConfigMsg;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created on 2023-05-18
 * Project user-service
 */
@Configuration(value = "querydsl")
//@RequiredArgsConstructor
public class QueryDslConfig {


    @PersistenceContext
    private EntityManager entityManager;

    public QueryDslConfig() {
        ConfigMsg.msg("QueryDsl");
    }

    @Bean
    public JPQLQueryFactory jPQLQueryFactory(){
        return new JPAQueryFactory(entityManager);
    }
}
