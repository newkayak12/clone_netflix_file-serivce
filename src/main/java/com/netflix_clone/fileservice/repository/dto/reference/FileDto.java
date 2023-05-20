package com.netflix_clone.fileservice.repository.dto.reference;

import com.netflix_clone.fileservice.enums.FileType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created on 2023-05-19
 * Project file-service
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

    @QueryProjection
    public FileDto(Long fileNo, Long tableNo, FileType fileType, String storedFileName, String originalFileName, Integer orders, String contentType, Long fileSize) {
        this.fileNo = fileNo;
        this.tableNo = tableNo;
        this.fileType = fileType;
        this.storedFileName = storedFileName;
        this.originalFileName = originalFileName;
        this.orders = orders;
        this.contentType = contentType;
        this.fileSize = fileSize;
    }
}
