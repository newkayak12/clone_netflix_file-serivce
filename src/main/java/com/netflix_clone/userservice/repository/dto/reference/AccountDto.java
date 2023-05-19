package com.netflix_clone.userservice.repository.dto.reference;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2023-05-10
 * Project user-service
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto implements Serializable {
    private Long userNo;
    private String userId;
    private String userPwd;
    private LocalDateTime regDate;
    private Boolean isAdult;
    private LocalDateTime adultCheckDate;
    private String mobileNo;
    private String email;
    private Boolean isSubscribed;
    private LocalDateTime lastSignDate;
    private List<ProfileDto> profiles = new ArrayList<>();

    @QueryProjection
    public AccountDto(Long userNo, String userId, String userPwd, LocalDateTime regDate, Boolean isAdult, LocalDateTime adultCheckDate, String mobileNo, String email, Boolean isSubscribed, LocalDateTime lastSignDate) {
        this.userNo = userNo;
        this.userId = userId;
        this.userPwd = userPwd;
        this.regDate = regDate;
        this.isAdult = isAdult;
        this.adultCheckDate = adultCheckDate;
        this.mobileNo = mobileNo;
        this.email = email;
        this.isSubscribed = isSubscribed;
        this.lastSignDate = lastSignDate;
    }
}
