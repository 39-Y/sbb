package com.mysite.sbb.domain;

public class DataNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1l;
    public DataNotFoundException(String message){
        super(message);
    }
}
