package com.netflix_clone.fileservice.configure.fileUpload;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration(value = "fileUpload")
@DependsOn(value = "constants")
public class FileUploadConfig {
//    private FileUpload fileUpload = new FileUpload(Constants.FILE_PATH, false, 1024L * 10L);
}
