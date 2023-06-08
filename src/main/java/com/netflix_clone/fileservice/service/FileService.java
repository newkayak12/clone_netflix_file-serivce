package com.netflix_clone.fileservice.service;

import com.netflix_clone.fileservice.enums.FileType;
import com.netflix_clone.fileservice.exceptions.BecauseOf;
import com.netflix_clone.fileservice.exceptions.CommonException;
import com.netflix_clone.fileservice.repository.domains.File;
import com.netflix_clone.fileservice.repository.dto.reference.FileDto;
import com.netflix_clone.fileservice.repository.dto.request.FileRequest;
import com.netflix_clone.fileservice.repository.dto.request.FileRequests;
import com.netflix_clone.fileservice.repository.fileRepository.FileRepository;
import com.sun.mail.iap.CommandFailedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.newkayak.FileUpload.FileResult;
import org.newkayak.FileUpload.FileUpload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class FileService {
    private final FileRepository repository;
    private final FileUpload fileUpload;
    private final ModelMapper mapper;

    public FileDto save(FileRequest request) {

        //TODO FileUploader => SAVE
        log.warn("??????? {}", fileUpload);

        FileDto results  = fileUpload.upload(true, request.getRawFile()).stream()
                                .findFirst()
                                .map( result -> {
                                    FileDto dto = this.resultToDto(result);
                                    this.migrateDto(dto, request);

                                    System.out.println(mapper.map(dto, File.class));
                                    dto = mapper.map(repository.save(mapper.map(dto, File.class)), FileDto.class);

                                    return dto;
                                })
                                .orElseGet(() -> null);
        return results;
    }

    public List<FileDto> saves(FileRequests requests) {
        List<FileResult> result =fileUpload.upload(true, requests.getRawFiles().toArray(MultipartFile[]::new));
        return result.stream().map( r -> {
            FileDto dto = this.resultToDto(r);
            this.migrateDto(dto, requests);
            dto = mapper.map(repository.save(mapper.map(dto, File.class)), FileDto.class);
            return dto;
        }).collect(Collectors.toList());
    }


    private FileDto resultToDto ( FileResult result ){
        return mapper.map(result, FileDto.class);
    }
    private void migrateDto( FileDto dto, FileRequest request ){
        dto.setTableNo(request.getTableNo());
        dto.setFileType(request.getFileType());
        if(Objects.nonNull(request.getOrders())) dto.setOrders(request.getOrders());
    }
    private void migrateDto( FileDto dto, FileRequests request ){
        dto.setTableNo(request.getTableNo());
        dto.setFileType(request.getFileType());
        if(Objects.nonNull(request.getOrders())) dto.setOrders(request.getOrders());
    }



    @Transactional(readOnly = true)
    public List<FileDto> files(Long tableNo, FileType fileType) { return repository.files(tableNo, fileType); }

    @Transactional(readOnly = true)
    public List<FileDto> files(List<Long> tableNos, FileType fileType) {
        return repository.files(tableNos, fileType);
    }

    @Transactional(readOnly = true)
    public FileDto file(Long tableNo, FileType fileType) {
        return repository.file(tableNo, fileType);
    }

    public Boolean remove(Long tableNo, FileType fileType) throws CommonException {
        List<FileDto> list = repository.files(tableNo, fileType);

        this.removeFile(list);

        return repository.remove(tableNo, fileType);
    }

    public Boolean remove(List<Long> tableNos, FileType fileType) throws CommonException {
        List<FileDto> list = repository.files(tableNos, fileType);
        this.removeFile(list);
        return repository.remove(tableNos, fileType);
    }

    public Boolean removeNotIn(List<FileDto> list) throws CommonException {
        FileDto dto = list.stream().findAny().orElseThrow(() -> new CommonException(BecauseOf.NO_DATA));
        List<FileDto> persistedList = repository.files(dto.getTableNo(), dto.getFileType());
        persistedList.removeAll(list);
        this.removeFile(persistedList);
        return repository.removeNotIn(list.stream().map(FileDto::getFileNo).collect(Collectors.toList()));
    }
    public Boolean removeIn(List<FileDto> list) throws CommonException {
        this.removeFile(list);
        return repository.removeIn(list.stream().map(FileDto::getFileNo).collect(Collectors.toList()));
    }




    private Boolean removeFile ( List<FileDto> list ) throws CommonException {
        List<FileResult> result = list.stream().map(dto -> mapper.map(dto, FileResult.class)).collect(Collectors.toList());

        // TODO FileUploader => remove
        if ( !result.isEmpty() && fileUpload.remove() <= 0 ) throw new CommonException(BecauseOf.DELETE_FAILURE);

        return true;
    }



}
