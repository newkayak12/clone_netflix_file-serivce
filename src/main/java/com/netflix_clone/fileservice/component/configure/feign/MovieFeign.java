package com.netflix_clone.fileservice.component.configure.feign;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@Qualifier(value = "movie")
@FeignClient(name = "netflix-clone-movie-service")
public interface MovieFeign {

}
