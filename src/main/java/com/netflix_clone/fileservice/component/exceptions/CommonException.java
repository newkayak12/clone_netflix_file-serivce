package com.netflix_clone.fileservice.component.exceptions;

public class CommonException extends Exception{

    public CommonException(BecauseOf reason){
        super(reason.getMsg());
    }
}
