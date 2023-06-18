package com.netflix_clone.fileservice.component.exceptions;

import lombok.Getter;

@Getter
public enum BecauseOf {
    ALREADY_EXIST_ACCOUNT("이미 존재하는 계정입니다."),
    NO_DATA("데이터가 없습니다."),
    ACCOUNT_NOT_EXIST("아이디 혹은 비밀번호를 확인해주세요."),

    SAVE_FAILURE("저장에 실패했습니다."),
    UPDATE_FAILURE("변경에 실패했습니다."),
    INSERT_FAILURE("등록에 실패했습니다."),
    DELETE_FAILURE("삭제에 실패했습니다."),
    PASSWORD_NOT_MATCHED("아이디 혹은 비밀번호를 확인해주세요.");


    private String msg;
    BecauseOf(String msg){
        this.msg = msg;
    }
}
