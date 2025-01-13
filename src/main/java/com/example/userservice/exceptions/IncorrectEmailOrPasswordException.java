package com.example.userservice.exceptions;

import lombok.Getter;

@Getter
public class IncorrectEmailOrPasswordException extends Exception{
    private String email;
    private String password;

    public IncorrectEmailOrPasswordException(String email,String password){
        super();
        this.email=email;
        this.password=password;
    }
}
