package com.netflix_clone.fileservice.configure.feign;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@Qualifier(value = "movie")
@FeignClient(name = "netflix-clone-movie-service")
public interface MovieFeign {

}
