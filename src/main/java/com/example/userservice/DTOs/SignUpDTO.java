package com.example.userservice.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpDTO {
    private String name;
    private String email;
    private String password;
}
