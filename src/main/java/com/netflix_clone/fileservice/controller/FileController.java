package com.netflix_clone.fileservice.controller;

import com.netflix_clone.fileservice.enums.FileType;
import com.netflix_clone.fileservice.exceptions.CommonException;
import com.netflix_clone.fileservice.repository.dto.reference.FileDto;
import com.netflix_clone.fileservice.repository.dto.request.FileRequest;
import com.netflix_clone.fileservice.repository.dto.request.FileRequests;
import com.netflix_clone.fileservice.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created on 2023-05-12
 * Project user-service
 */
@RestController
@RequestMapping(value = "/api/v1/file")
@RequiredArgsConstructor
public class FileController {
    private final FileService service;


    @PostMapping(value = "/save", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<FileDto> save(@ModelAttribute FileRequest request) {
        return new ResponseEntity(service.save(request), HttpStatus.OK);
    }
    @PostMapping(value = "/saves", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<List<FileDto>> saves(@ModelAttribute FileRequests requests) {
        return new ResponseEntity(service.saves(requests), HttpStatus.OK);
    }

    @GetMapping(value = "/{tableNo}/{fileType}")
    public ResponseEntity<List<FileDto>> files(@PathVariable Long tableNo, @PathVariable  FileType fileType) {
        return new ResponseEntity(service.files(tableNo, fileType), HttpStatus.OK);
    }

    @GetMapping(value = "/{tableNo}/{fileType}/mono")
    public ResponseEntity<FileDto> file(@PathVariable Long tableNo, @PathVariable FileType fileType) {
        return new ResponseEntity(service.file(tableNo, fileType), HttpStatus.OK);
    }

    @GetMapping(value = "/{fileType}")
    public ResponseEntity<List<FileDto>> files(@ModelAttribute List<Long> tableNos, @PathVariable  FileType fileType) {
        return new ResponseEntity(service.files(tableNos, fileType), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{tableNo}/{fileType}")
    public ResponseEntity<Boolean> remove(@PathVariable Long tableNo, @PathVariable  FileType fileType) throws CommonException {
        return new ResponseEntity<Boolean>(service.remove(tableNo ,fileType), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{fileType}")
    public ResponseEntity<Boolean> remove(@ModelAttribute List<Long> tableNos, @PathVariable  FileType fileType) throws CommonException {
        return new ResponseEntity<Boolean>(service.remove(tableNos ,fileType), HttpStatus.OK);
    }

    @DeleteMapping(value = "/include")
    public ResponseEntity<Boolean> removeIn(@ModelAttribute List<FileDto> files) throws CommonException {
        return new ResponseEntity<>(service.removeIn(files), HttpStatus.OK);
    }
    @DeleteMapping(value = "/exclude")
    public ResponseEntity<Boolean> removeNotIn(@ModelAttribute List<FileDto> files) throws CommonException {
        return new ResponseEntity<>(service.removeNotIn(files), HttpStatus.OK);
    }
}
