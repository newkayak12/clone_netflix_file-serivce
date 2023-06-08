package com.netflix_clone.fileservice.repository;

import com.netflix_clone.fileservice.exceptions.BecauseOf;
import com.netflix_clone.fileservice.repository.fileRepository.FileRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.config.BootstrapMode;

import static org.assertj.core.api.Assertions.*;
@DataJpaTest(bootstrapMode = BootstrapMode.DEFAULT, showSql = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(includeFilters = {
        @ComponentScan.Filter(
                type = FilterType.ANNOTATION,
                classes = {Configuration.class}
        )
})
@Profile("local")
public class FileRepositoryTest {

    @Autowired
    private FileRepository repository;

}
