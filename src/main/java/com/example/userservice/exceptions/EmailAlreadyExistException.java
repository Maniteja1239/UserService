package com.example.userservice.exceptions;

import jakarta.persistence.GeneratedValue;
import lombok.Getter;

@Getter
public class EmailAlreadyExistException extends Exception{
    private String email;
    public EmailAlreadyExistException(String email){
        super();
        this.email=email;
    }
}
