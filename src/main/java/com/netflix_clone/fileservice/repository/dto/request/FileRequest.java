package com.netflix_clone.fileservice.repository.dto.request;

import com.netflix_clone.fileservice.repository.dto.reference.FileDto;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created on 2023-05-19
 * Project file-service
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class FileRequest extends FileDto {
    private MultipartFile rawFile;
}
