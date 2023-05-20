package com.netflix_clone.fileservice.service;

import com.netflix_clone.fileservice.enums.FileType;
import com.netflix_clone.fileservice.repository.dto.reference.FileDto;
import com.netflix_clone.fileservice.repository.dto.request.FileRequest;
import com.netflix_clone.fileservice.repository.fileRepository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class FileService {
    private final FileRepository repository;

    public List<FileDto> save(List<FileRequest> requestList) {

        //TODO FileUploader => SAVE

        return new ArrayList<>();
    }


    @Transactional(readOnly = true)
    public List<FileDto> files(Long tableNo, FileType fileType) { return repository.files(tableNo, fileType); }

    @Transactional(readOnly = true)
    public List<FileDto> files(List<Long> tableNos, FileType fileType) {
        return repository.files(tableNos, fileType);
    }


    public Boolean remove(Long tableNo, FileType fileType) {
        List<FileDto> list = repository.files(tableNo, fileType);

        // TODO FileUploader => remove

        return repository.remove(tableNo, fileType);
    }

    public Boolean remove(List<Long> tableNos, FileType fileType) {
        List<FileDto> list = repository.files(tableNos, fileType);

        // TODO FileUploader => remove

        return repository.remove(tableNos, fileType);
    }
}
