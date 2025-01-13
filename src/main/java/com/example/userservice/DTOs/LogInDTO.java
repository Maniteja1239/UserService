package com.example.userservice.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogInDTO {
    private String email;
    private String password;
}
