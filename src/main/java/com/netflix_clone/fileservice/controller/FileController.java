package com.netflix_clone.fileservice.controller;

import com.netflix_clone.fileservice.component.enums.FileType;
import com.netflix_clone.fileservice.component.exceptions.CommonException;
import com.netflix_clone.fileservice.repository.dto.reference.FileDto;
import com.netflix_clone.fileservice.repository.dto.request.FileRequest;
import com.netflix_clone.fileservice.repository.dto.request.FileRequests;
import com.netflix_clone.fileservice.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    @CachePut(key = "{#tableNo, #fileType}", value = "mono", cacheManager = "gsonCacheManager")
    public ResponseEntity<FileDto> save(@ModelAttribute FileRequest request) {
        return new ResponseEntity(service.save(request), HttpStatus.OK);
    }
    @PostMapping(value = "/saves", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @CachePut(key = "{#requests.tableNo, #requests.fileType}", value = {"files_no"}, cacheManager = "gsonCacheManager")
    public ResponseEntity<List<FileDto>> saves(@ModelAttribute FileRequests requests) {
        return new ResponseEntity(service.saves(requests), HttpStatus.OK);
    }

    @GetMapping(value = "/{tableNo}/{fileType}/mono")
    @Cacheable(key = "{#tableNo, #fileType}", value = "mono", cacheManager = "gsonCacheManager")
    public ResponseEntity<FileDto> file(@PathVariable Long tableNo, @PathVariable FileType fileType) {
        return new ResponseEntity(service.file(tableNo, fileType), HttpStatus.OK);
    }

    @GetMapping(value = "/{tableNo}/{fileType}")
    @Cacheable(key = "{#tableNo, #fileType}", value = "files_no", cacheManager = "gsonCacheManager")
    public ResponseEntity<List<FileDto>> files(@PathVariable Long tableNo, @PathVariable  FileType fileType) {
        return new ResponseEntity(service.files(tableNo, fileType), HttpStatus.OK);
    }

    @GetMapping(value = "/{fileType}")
    @Cacheable(key = "{#tableNos, #fileType}", value = "files_nos", cacheManager = "gsonCacheManager")
    public ResponseEntity<List<FileDto>> files(@RequestParam(value = "tableNos") List<Long> tableNos, @PathVariable  FileType fileType) {
        return new ResponseEntity(service.files(tableNos, fileType), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{tableNo}/{fileType}")
    @CacheEvict(key = "{#tableNo, #fileType}", value= {"mono", "files_no"}, cacheManager = "gsonCacheManager")
    public ResponseEntity<Boolean> remove(@PathVariable Long tableNo, @PathVariable  FileType fileType) throws CommonException {
        return new ResponseEntity<Boolean>(service.remove(tableNo ,fileType), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{fileType}")
    @CacheEvict(key = "{#tableNos, #fileType}", value = {"files_nos"}, cacheManager = "gsonCacheManager")
    public ResponseEntity<Boolean> remove(@RequestParam(value = "tableNos") List<Long> tableNos, @PathVariable  FileType fileType) throws CommonException {
        return new ResponseEntity<Boolean>(service.remove(tableNos ,fileType), HttpStatus.OK);
    }

    @DeleteMapping(value = "/include")
    public ResponseEntity<Boolean> removeIn(@RequestParam(value = "fileNos") List<Long> fileNos) throws CommonException {
        return new ResponseEntity<>(service.removeIn(fileNos), HttpStatus.OK);
    }
    @DeleteMapping(value = "/exclude")
    public ResponseEntity<Boolean> removeNotIn(@RequestParam(value = "fileNos") List<Long> fileNos) throws CommonException {
        return new ResponseEntity<>(service.removeNotIn(fileNos), HttpStatus.OK);
    }
}
