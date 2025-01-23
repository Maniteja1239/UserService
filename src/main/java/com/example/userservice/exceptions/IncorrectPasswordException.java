package com.example.userservice.exceptions;

import lombok.Getter;

@Getter
public class IncorrectPasswordException extends Exception{
    private String email;
    private String password;

    public IncorrectPasswordException(){
        super();
    }
}
