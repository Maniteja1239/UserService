package com.example.userservice.exceptions;


import lombok.Getter;

@Getter
public class EmailDoesNotExistException extends Exception{
    private String email;
    public EmailDoesNotExistException(String email){
        super();
        this.email=email;
    }
}
