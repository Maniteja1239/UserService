package com.example.userservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity(name = "Tokens")
public class Token extends BaseModel{
    private String value;
    @ManyToOne
    @JoinColumn
    private User user;
    private Date expiryAt;
}
