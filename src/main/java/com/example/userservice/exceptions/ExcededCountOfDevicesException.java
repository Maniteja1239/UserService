package com.example.userservice.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ExcededCountOfDevicesException extends Exception{
    private String email;
    public ExcededCountOfDevicesException(String email){
        super();
        this.email=email;
    }
}
