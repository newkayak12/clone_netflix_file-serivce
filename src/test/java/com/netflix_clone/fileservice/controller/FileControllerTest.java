package com.netflix_clone.fileservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix_clone.fileservice.enums.FileType;
import com.netflix_clone.fileservice.repository.dto.request.FileRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@EnableConfigurationProperties
@ActiveProfiles(value = {"local"})
public class FileControllerTest {
    private final String prefix = "/api/v1/file";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @DisplayName("저장 테스트")
    @Nested
    class Save {
        private MockMultipartFile file;

        @BeforeEach
        public void setUp() throws IOException {
            InputStream inputStream = new FileInputStream("/Users/sanghyeonkim/Downloads/R1280x0.png");
            file = new MockMultipartFile("file", "file.png", "png", inputStream);
        }


        @Test
        @DisplayName("단건")
        public void single() throws Exception {
            FileRequest fileRequest = new FileRequest();
            fileRequest.setFileNo(200L);
            fileRequest.setFileType(FileType.TICKET);
            fileRequest.setRawFile(file);


            System.out.println(file);
            mockMvc.perform(
                    multipart(prefix+"/save")
                    .file("rawFile", file.getBytes())
                    .queryParam("fileNo", "200")
                    .queryParam("fileType", FileType.TICKET.name())

            )
            .andExpect(status().isOk())
            .andDo(print());

        }

        @Test
        @DisplayName("다중")
        public void multi(){

        }
    }

}