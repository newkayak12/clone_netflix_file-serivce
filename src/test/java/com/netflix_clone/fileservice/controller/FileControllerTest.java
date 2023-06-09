package com.netflix_clone.fileservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix_clone.fileservice.enums.FileType;
import com.netflix_clone.fileservice.repository.dto.request.FileRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


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

        public MockMultipartFile getMockMultiPartFile(String name) {
            try {
                InputStream inputStream = new FileInputStream("/Users/sanghyeonkim/Downloads/R1280x0.png");
                return new MockMultipartFile("rawFile", "file.png", "png", inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }



        @Test
        @DisplayName("단건")
        @Transactional
        @Rollback(value = true)
        public void single() throws Exception {
            MockMultipartFile rawFile = this.getMockMultiPartFile("rawFile");

            mockMvc.perform(
                    multipart(prefix+"/save")
                    .file(rawFile)
                    .queryParam("tableNo", "200")
                    .queryParam("fileType", "TICKET")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("tableNo").value(200))
            .andExpect(jsonPath("fileType").value(FileType.TICKET.name()))
            .andExpect(jsonPath("storedFileName").exists())
            .andExpect(jsonPath("originalFileName").value("file.png"))
            .andExpect(jsonPath("contentType").value("png"))
            .andDo(print());

        }

        @Test
        @DisplayName("다중")
        @Transactional
        @Rollback(value = true)
        public void multi() throws Exception {
            MockMultipartFile rawFiles = this.getMockMultiPartFile("rawFiles");

            mockMvc.perform(
                            multipart(prefix+"/saves")
                                    .file(rawFiles)
                                    .file(rawFiles)
                                    .queryParam("tableNo", "200")
                                    .queryParam("fileType", "TICKET")
                    )
                    .andExpect(status().isOk())
//                    .andExpect(jsonPath("tableNo").value(200))
//                    .andExpect(jsonPath("fileType").value(FileType.TICKET.name()))
//                    .andExpect(jsonPath("storedFileName").exists())
//                    .andExpect(jsonPath("originalFileName").value("file.png"))
//                    .andExpect(jsonPath("contentType").value("png"))
                    .andDo(print());

        }
    }

}