package com.netflix_clone.fileservice.component.configure.fileUpload;

import com.netflix_clone.fileservice.component.configure.ConfigMsg;
import com.netflix_clone.fileservice.component.constants.Constants;
import org.newkayak.FileUpload.FileUpload;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration(value = "fileUploadConfig")
@DependsOn(value = "constants")
public class FileUploadConfig {
    public FileUploadConfig() {
        ConfigMsg.msg("FileUpload");
    }

    @Bean
    public FileUpload fileUpload() {
        return new FileUpload(Constants.FILE_PATH, true, 1024L * 10L);
    }

}
