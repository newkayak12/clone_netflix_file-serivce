package com.netflix_clone.fileservice.component.configure.objectMapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.netflix_clone.fileservice.component.configure.ConfigMsg;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration(value = "objectMapperConfig")
//@Component
public class ObjectMapperConfig {
    private  ObjectMapper objectMapper;

    public ObjectMapperConfig() {
        this.objectMapper = new ObjectMapper();
        ConfigMsg.msg("ObjectMapper");
    }

    private void deserializeWhenEmptyCase() {
        this.objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
        this.objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    }
    private void deserializeWhenUnknownCase() {
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    private void deserializeWhenEnumCase() {
        this.objectMapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
        this.objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
    }
    private void deserializeRegisterJavaTimeModule() {
        objectMapper.registerModule(new JavaTimeModule());
    }
    private void deserializeSettings() {
        this.deserializeWhenEnumCase();
        this.deserializeWhenEmptyCase();
        this.deserializeWhenUnknownCase();
        this.deserializeRegisterJavaTimeModule();
    }

    private void serializeSettings() {
        this.objectMapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING,true);
        this.objectMapper.configure(SerializationFeature.WRITE_SELF_REFERENCES_AS_NULL,true);
        this.objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
    }


    @Bean
    public ObjectMapper objectMapper () {
        this.deserializeSettings();
        this.serializeSettings();
        return this.objectMapper;
    }

}
