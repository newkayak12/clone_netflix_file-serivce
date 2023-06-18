package com.netflix_clone.fileservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix_clone.fileservice.component.enums.FileType;
import com.netflix_clone.fileservice.util.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@EnableConfigurationProperties
@ActiveProfiles(value = {"local"})
@AutoConfigureRestDocs
public class FileControllerTest extends AbstractControllerTest {
    private final String prefix = "/api/v1/file";

//    @Autowired
//    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("저장 테스트")
    @Nested
    class Save {

        public MockMultipartFile getMockMultiPartFile(String name) {
            try {
                InputStream inputStream = new FileInputStream("/Users/sanghyeonkim/Downloads/R1280x0.png");
                return new MockMultipartFile(name, "file.png", "image/png", inputStream);
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
            .andExpect(jsonPath("contentType").value("image/png"));

        }

        @Test
        @DisplayName("다중")
        @Transactional
        @Rollback(value = true)
        public void multi() throws Exception {
            MockMultipartFile rawFiles = this.getMockMultiPartFile("rawFiles");

            mockMvc.perform(
                            multipart(prefix+"/saves", rawFiles, rawFiles)
                                    .file(rawFiles)
                                    .file(rawFiles)
                                    .file(rawFiles)
                                    .file(rawFiles)
                                    .queryParam("tableNo", "203")
                                    .queryParam("fileType", "TICKET")
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()").value(4))
                    .andExpect(jsonPath("$[0].tableNo").value(203))
                    .andExpect(jsonPath("$[0].fileType").value(FileType.TICKET.name()))
                    .andExpect(jsonPath("$[0].storedFileName").exists())
                    .andExpect(jsonPath("$[0].originalFileName").value("file.png"))
                    .andExpect(jsonPath("$[0].contentType").value("image/png"))

                    .andExpect(jsonPath("$[1].tableNo").value(203))
                    .andExpect(jsonPath("$[1].fileType").value(FileType.TICKET.name()))
                    .andExpect(jsonPath("$[1].storedFileName").exists())
                    .andExpect(jsonPath("$[1].originalFileName").value("file.png"))
                    .andExpect(jsonPath("$[1].contentType").value("image/png"))

                    .andExpect(jsonPath("$[2].tableNo").value(203))
                    .andExpect(jsonPath("$[2].fileType").value(FileType.TICKET.name()))
                    .andExpect(jsonPath("$[2].storedFileName").exists())
                    .andExpect(jsonPath("$[2].originalFileName").value("file.png"))
                    .andExpect(jsonPath("$[2].contentType").value("image/png"))

                    .andExpect(jsonPath("$[3].tableNo").value(203))
                    .andExpect(jsonPath("$[3].fileType").value(FileType.TICKET.name()))
                    .andExpect(jsonPath("$[3].storedFileName").exists())
                    .andExpect(jsonPath("$[3].originalFileName").value("file.png"))
                    .andExpect(jsonPath("$[3].contentType").value("image/png"))
                    .andDo(print());

        }
    }

    @DisplayName(value = "파일 가져오기")
    @Nested
    public class Fetch {
        @DisplayName("다중 - 특정 tableNo")
        @Test
        public void multi () throws Exception {
            mockMvc.perform(
                    get(String.format("%s/%d/%s", prefix, 201, FileType.TICKET.name()))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(2))
            .andExpect(jsonPath("$[0].tableNo").value(201))
            .andExpect(jsonPath("$[0].fileType").value(FileType.TICKET.name()))
            .andExpect(jsonPath("$[0].storedFileName").exists())
            .andExpect(jsonPath("$[0].originalFileName").value("file.png"))
            .andExpect(jsonPath("$[0].contentType").value("png"))

            .andExpect(jsonPath("$[1].tableNo").value(201))
            .andExpect(jsonPath("$[1].fileType").value(FileType.TICKET.name()))
            .andExpect(jsonPath("$[1].storedFileName").exists())
            .andExpect(jsonPath("$[1].originalFileName").value("file.png"))
            .andExpect(jsonPath("$[1].contentType").value("png"))
            .andDo(print());
        }

        @DisplayName("단일 - 특정 tableNo")
        @Test
        public void single () throws Exception {
            mockMvc.perform(
                    get(String.format("%s/%d/%s/mono", prefix, 200, FileType.TICKET.name()))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("tableNo").value(200))
            .andExpect(jsonPath("fileType").value(FileType.TICKET.name()))
            .andExpect(jsonPath("storedFileName").exists())
            .andExpect(jsonPath("originalFileName").value("file.png"))
            .andExpect(jsonPath("contentType").value("png"))
            .andDo(print());
        }

        @DisplayName("다중 - 다중 tableNo")
        @Test
        public void multiFilesAndMultiTableNo () throws Exception {
            mockMvc.perform(
                    get(String.format("%s/%s", prefix, FileType.TICKET.name()))
                            .queryParam("tableNos", "200")
                            .queryParam("tableNos", "201")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(3))
            .andExpect(jsonPath("$[0].tableNo").value(200))
            .andExpect(jsonPath("$[0].fileType").value(FileType.TICKET.name()))
            .andExpect(jsonPath("$[0].storedFileName").exists())
            .andExpect(jsonPath("$[0].originalFileName").value("file.png"))
            .andExpect(jsonPath("$[0].contentType").value("png"))

            .andExpect(jsonPath("$[1].tableNo").value(201))
            .andExpect(jsonPath("$[1].fileType").value(FileType.TICKET.name()))
            .andExpect(jsonPath("$[1].storedFileName").exists())
            .andExpect(jsonPath("$[1].originalFileName").value("file.png"))
            .andExpect(jsonPath("$[1].contentType").value("png"))

            .andExpect(jsonPath("$[2].tableNo").value(201))
            .andExpect(jsonPath("$[2].fileType").value(FileType.TICKET.name()))
            .andExpect(jsonPath("$[2].storedFileName").exists())
            .andExpect(jsonPath("$[2].originalFileName").value("file.png"))
            .andExpect(jsonPath("$[2].contentType").value("png"))
            .andDo(print());
        }

    }

    @DisplayName(value = "파일 삭제")
    @Nested
    public class Remove {

        @Test
        @DisplayName(value = "단일 삭제")
        @Transactional
        @Rollback(value = true)
        public void single () throws Exception {
            mockMvc.perform(
                delete(String.format("%s/%d/%s", prefix, 203, FileType.TICKET.name()))
            )
            .andExpect(status().isOk())
            .andExpect(content().string("true"))
            .andDo(print());
        }

        @Test
        @DisplayName(value = "다중 삭제")
        @Transactional
        @Rollback(value = true)
        public void multi () throws Exception {
            mockMvc.perform(
                    delete(String.format("%s/%s", prefix, FileType.TICKET.name()))
                    .queryParam("tableNos", "200")
                    .queryParam("tableNos", "201")
            )
            .andExpect(status().isOk())
            .andExpect(content().string("true"))
            .andDo(print());
        }

        @Test
        @DisplayName(value = "특정 파일 삭제")
        @Transactional
        @Rollback
        public void include () throws Exception {
            mockMvc.perform(
                delete(String.format("%s/include", prefix))
                .queryParam("fileNos", "25")
                .queryParam("fileNos", "26")
            )
            .andExpect(status().isOk())
            .andExpect(content().string("true"))
            .andDo(print());
        }

        @Test
        @DisplayName(value = "특정 파일 제외하고 삭제")
        @Transactional
        @Rollback
        public void notInclude () throws Exception {

            //35 ~ 38
            mockMvc.perform(
                            delete(String.format("%s/include", prefix))
                                    .queryParam("fileNos", "36")
                                    .queryParam("fileNos", "38")
                    )
                    .andExpect(status().isOk())
                    .andExpect(content().string("true"))
                    .andDo(print());
        }
    }
}