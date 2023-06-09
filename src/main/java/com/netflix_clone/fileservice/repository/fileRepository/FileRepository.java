package com.netflix_clone.fileservice.repository.fileRepository;

import com.netflix_clone.fileservice.component.enums.FileType;
import com.netflix_clone.fileservice.repository.domains.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long>, FileRepositoryCustom {
    @Query(nativeQuery = true, value = "SELECT :wakeUpMsg FROM DUAL;")
    String wakeUp(@Param(value = "wakeUpMsg") String wakeUpMsg);

    List<File> findFilesByFileNoIn ( List<Long> fileNo );
    List<File> findFilesByTableNoAndFileType(Long tableNo, FileType fileType );
}
