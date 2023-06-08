package com.netflix_clone.fileservice.repository.fileRepository;

import com.netflix_clone.fileservice.enums.FileType;
import com.netflix_clone.fileservice.repository.dto.reference.FileDto;

import java.util.List;

public interface FileRepositoryCustom {
    List<FileDto> files(Long tableNo, FileType fileType);
    List<FileDto> files(List<Long> tableNos, FileType fileType);
    FileDto file(Long tableNo, FileType fileType);
    Boolean remove(Long tableNo, FileType fileType);
    Boolean remove(List<Long> tableNos, FileType fileType);
    Boolean removeNotIn ( List<Long> fileNos );
    Boolean removeIn ( List<Long> fileNos );

}
