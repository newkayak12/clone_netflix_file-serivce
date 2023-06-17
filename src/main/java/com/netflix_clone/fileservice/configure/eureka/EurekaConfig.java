package com.netflix_clone.fileservice.configure.eureka;

import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;

/**
 * Created on 2023-05-12
 * Project board-service
 */
@Configuration (value = "eureka_configuration")
@EnableEurekaClient
public class EurekaConfig {

    public EurekaConfig() {
    }
}
