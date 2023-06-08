package com.netflix_clone.fileservice.configure.modelMapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(value = "modelMapperConfig")
public class ModelMapperConfig {
    private ModelMapper modelMapper = new ModelMapper();

    private void strictStrategy() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }
    private void useReflection() {
        this.modelMapper.getConfiguration()
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);
    }
    @Bean
    public ModelMapper modelMapper() {
        this.strictStrategy();
        this.useReflection();
        return modelMapper;
    }
}
