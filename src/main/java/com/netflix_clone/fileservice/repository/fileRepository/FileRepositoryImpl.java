package com.netflix_clone.fileservice.repository.fileRepository;

import com.netflix_clone.fileservice.component.enums.FileType;
import com.netflix_clone.fileservice.repository.domains.File;
import com.netflix_clone.fileservice.repository.dto.reference.FileDto;
import com.netflix_clone.fileservice.repository.dto.reference.QFileDto;
import com.querydsl.jpa.JPQLQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.netflix_clone.fileservice.repository.domains.QFile.file;
/**
 * Created on 2023-05-18
 * Project user-service
 */
public class FileRepositoryImpl extends QuerydslRepositorySupport implements FileRepositoryCustom {
    private JPQLQueryFactory query;

    @Autowired
    public FileRepositoryImpl(JPQLQueryFactory query) {
        super(File.class);
        this.query = query;
    }



    @Override
    public List<FileDto> files(Long tableNo, FileType fileType) {
        return query
                .select(new QFileDto(
                                file.fileNo,
                                file.tableNo,
                                file.fileType,
                                file.storedFileName,
                                file.originalFileName,
                                file.orders,
                                file.contentType,
                                file.fileSize
                        ))
                .from(file)
                .where(
                        file.tableNo.eq(tableNo)
                       .and(file.fileType.eq(fileType))
                )
                .orderBy(file.orders.asc())
                .fetch();
    }

    @Override
    public List<FileDto> files(List<Long> tableNos, FileType fileType) {
        return query
                .select(new QFileDto(
                        file.fileNo,
                        file.tableNo,
                        file.fileType,
                        file.storedFileName,
                        file.originalFileName,
                        file.orders,
                        file.contentType,
                        file.fileSize
                ))
                .from(file)
                .where(
                        file.tableNo.in(tableNos)
                   .and(file.fileType.eq(fileType))
                )
                .orderBy(file.tableNo.asc(), file.orders.asc(), file.fileNo.asc())
                .fetch();
    }

    @Override
    public FileDto file(Long tableNo, FileType fileType) {
        return query
                .select(new QFileDto(
                        file.fileNo,
                        file.tableNo,
                        file.fileType,
                        file.storedFileName,
                        file.originalFileName,
                        file.orders,
                        file.contentType,
                        file.fileSize
                ))
                .from(file)
                .where(
                        file.tableNo.eq(tableNo)
                                .and(file.fileType.eq(fileType))
                )
                .fetchOne();
    }

    @Override
    public Boolean remove(Long tableNo, FileType fileType) {
        return query.delete(file).where(file.tableNo.eq(tableNo).and(file.fileType.eq(fileType))).execute() > 0;
    }

    @Override
    public Boolean remove(List<Long> tableNos, FileType fileType) {
        return query.delete(file).where(file.tableNo.in(tableNos).and(file.fileType.eq(fileType))).execute() > 0;
    }

    @Override
    public Boolean removeNotIn(List<Long> fileNos) {
        return query.delete(file).where(file.fileNo.notIn(fileNos)).execute() > 0;
    }

    @Override
    public Boolean removeIn(List<Long> fileNos) {
        return query.delete(file).where(file.fileNo.in(fileNos)).execute() > 0;
    }
}
