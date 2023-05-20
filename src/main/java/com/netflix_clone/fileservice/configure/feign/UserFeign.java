package com.netflix_clone.fileservice.configure.feign;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created on 2023-05-12
 * Project user-service
 */
@Component
@Qualifier(value = "user")
@FeignClient(value = "netflix-clone-user-service")
public interface UserFeign {

}
