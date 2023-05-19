package com.netflix_clone.userservice.repository.dto.reference;

import com.netflix_clone.userservice.enums.FileType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created on 2023-05-19
 * Project user-service
 */
@Data
@NoArgsConstructor
public class FileDto implements Serializable {
    private Long fileNo;
    private Long tableNo;
    private FileType fileType;
    private String storedFileName;
    private String originalFileName;
    private Integer orders;
    private String contentType;
    private Long fileSize;
}
