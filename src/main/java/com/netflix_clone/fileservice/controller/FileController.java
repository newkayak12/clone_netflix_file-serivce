package com.netflix_clone.fileservice.controller;

import com.netflix_clone.fileservice.enums.FileType;
import com.netflix_clone.fileservice.repository.dto.reference.FileDto;
import com.netflix_clone.fileservice.repository.dto.request.FileRequest;
import com.netflix_clone.fileservice.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created on 2023-05-12
 * Project user-service
 */
@RestController
@RequestMapping(value = "/api/v1/file")
@RequiredArgsConstructor
public class FileController {
    private final FileService service;

    @PostMapping(value = "/", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<List<FileDto>> save(@ModelAttribute List<FileRequest> requestList) {
        return new ResponseEntity(service.save(requestList), HttpStatus.OK);
    }

    @GetMapping(value = "/{tableNo}/{fileType}")
    public ResponseEntity<List<FileDto>> files(@PathVariable Long tableNo, @PathVariable  FileType fileType) {
        return new ResponseEntity(service.files(tableNo, fileType), HttpStatus.OK);
    }

    @GetMapping(value = "/{fileType}")
    public ResponseEntity<List<FileDto>> files(@ModelAttribute List<Long> tableNos, @PathVariable  FileType fileType) {
        return new ResponseEntity(service.files(tableNos, fileType), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{tableNo}/{fileType}")
    public ResponseEntity<Boolean> remove(@PathVariable Long tableNo, @PathVariable  FileType fileType) {
        return new ResponseEntity<Boolean>(service.remove(tableNo ,fileType), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{fileType}")
    public ResponseEntity<Boolean> remove(@ModelAttribute List<Long> tableNos, @PathVariable  FileType fileType) {
        return new ResponseEntity<Boolean>(service.remove(tableNos ,fileType), HttpStatus.OK);
    }
}
