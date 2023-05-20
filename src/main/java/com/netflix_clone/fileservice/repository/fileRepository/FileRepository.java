package com.netflix_clone.fileservice.repository.fileRepository;

import com.netflix_clone.fileservice.repository.domains.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long>, FileRepositoryCustom {
}
