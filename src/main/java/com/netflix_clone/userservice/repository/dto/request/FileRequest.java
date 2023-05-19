package com.netflix_clone.userservice.repository.dto.request;

import com.netflix_clone.userservice.repository.dto.reference.FileDto;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created on 2023-05-19
 * Project user-service
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class FileRequest extends FileDto {
    private MultipartFile rawFile;
}

