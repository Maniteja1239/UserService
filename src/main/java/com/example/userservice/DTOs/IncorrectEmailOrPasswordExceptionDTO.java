package com.example.userservice.DTOs;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class IncorrectEmailOrPasswordExceptionDTO {
    private String message;
}
