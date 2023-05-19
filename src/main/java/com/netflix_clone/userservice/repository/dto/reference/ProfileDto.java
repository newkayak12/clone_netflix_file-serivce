package com.netflix_clone.userservice.repository.dto.reference;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created on 2023-05-10
 * Project user-service
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto implements Serializable {
    private Long profileNo;
    private AccountDto account;
    private String profileName;
    private LocalDateTime regDate;
    private Boolean isPush;
    private LocalDateTime lastSignInDate;

    @Transient
    private FileDto profileImage;


    @QueryProjection
    public ProfileDto(Long profileNo, String profileName, LocalDateTime regDate, Boolean isPush) {
        this.profileNo = profileNo;
        this.profileName = profileName;
        this.regDate = regDate;
        this.isPush = isPush;
    }
}
