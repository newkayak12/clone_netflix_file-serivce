package com.netflix_clone.fileservice.repository.dto.request;

import com.netflix_clone.fileservice.repository.dto.reference.FileDto;
import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created on 2023-05-19
 * Project file-service
 */
@Data
@ToString(callSuper = true)
public class FileRequests extends FileDto {

    private List<MultipartFile> rawFiles;
}
