package com.netflix_clone.userservice.configure.feign;

import com.netflix_clone.userservice.enums.FileType;
import com.netflix_clone.userservice.repository.dto.reference.FileDto;
import com.netflix_clone.userservice.repository.dto.request.FileRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@Qualifier(value = "file")
@FeignClient(name = "netflix-clone-file-service")
public interface FileFeign {

    @PostMapping(value = "/api/v1/file/", produces = {MediaType.MULTIPART_FORM_DATA_VALUE})
    ResponseEntity<List<FileDto>> save(@ModelAttribute  List<FileRequest> requestList);
    @GetMapping(value = "/api/v1/file/{tableNo}/{fileType}")
    ResponseEntity<List<FileDto>> files(@PathVariable(name = "tableNo") Long tableNo, @PathVariable(name = "fileType") FileType fileType);
    @GetMapping(value = "/api/v1/file/{fileType}")
    ResponseEntity<List<FileDto>> files(@ModelAttribute List<Long> tableNos, @PathVariable(name = "fileType")  FileType fileType);
    @DeleteMapping(value = "/api/v1/file/{tableNo}/{fileType}")
    ResponseEntity<Boolean> remove(@PathVariable(name = "tableNo") Long tableNo, @PathVariable(name = "fileType")  FileType fileType);
    @DeleteMapping(value = "/api/v1/file/{fileType}")
    ResponseEntity<Boolean> remove(@ModelAttribute List<Long> tableNos, @PathVariable(name = "fileType")  FileType fileType);
}
