package com.netflix_clone.fileservice.repository.domains;

import com.netflix_clone.fileservice.enums.FileType;
import lombok.ToString;

import javax.persistence.*;

/**
 * Created on 2023-05-19
 * Project file-service
 */
@Table(name = "file")
@Entity
@Cacheable(value = true)
@ToString
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fileNo", columnDefinition = "BIGINT(20)")
    private Long fileNo;
    @Column(name = "tableNo", columnDefinition = "BIGINT(20)", nullable = false)
    private Long tableNo;
    @Column(name = "fileType", columnDefinition = "VARCHAR(200)", nullable = false)
    @Enumerated(EnumType.STRING)
    private FileType fileType;
    @Column(name = "storedFileName", columnDefinition = "VARCHAR(500)")
    private String storedFileName;
    @Column(name = "originalFileName", columnDefinition = "VARCHAR(500)")
    private String originalFileName;
    @Column(name = "orders", columnDefinition = "INT(11)", nullable = true)
    private Integer orders;
    @Column(name = "contentType", columnDefinition = "VARCHAR(500)")
    private String contentType;
    @Column(name = "fileSize", columnDefinition = "BIGINT(20)")
    private Long fileSize;
}
