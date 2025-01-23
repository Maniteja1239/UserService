package com.example.userservice.exceptionHandler;

import com.example.userservice.DTOs.EmailAlreadyExistExceptionDTO;
import com.example.userservice.DTOs.EmailDoesNotExistExceptionDTO;
import com.example.userservice.DTOs.IncorrectEmailOrPasswordExceptionDTO;
import com.example.userservice.exceptions.EmailAlreadyExistException;
import com.example.userservice.exceptions.EmailDoesNotExistException;
import com.example.userservice.exceptions.IncorrectPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(EmailDoesNotExistException.class)
    public ResponseEntity<EmailDoesNotExistExceptionDTO> EmailDoesNotExistExceptionHandler(EmailDoesNotExistException exception){
        EmailDoesNotExistExceptionDTO emailDoesNotExistExceptionDTO=new EmailDoesNotExistExceptionDTO();
        emailDoesNotExistExceptionDTO.setMessage("Email: "+exception.getEmail()+" does not exist");
        ResponseEntity<EmailDoesNotExistExceptionDTO> response=new ResponseEntity<>(emailDoesNotExistExceptionDTO,HttpStatus.BAD_REQUEST);
        return response;
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<IncorrectEmailOrPasswordExceptionDTO> IncorrectEmailOrPasswordExceptionHandler(IncorrectPasswordException exception){
        IncorrectEmailOrPasswordExceptionDTO dto=new IncorrectEmailOrPasswordExceptionDTO();
        dto.setMessage("Entered password is incorrect");
        ResponseEntity<IncorrectEmailOrPasswordExceptionDTO> response=new ResponseEntity<>(dto,HttpStatus.BAD_REQUEST);
        return response;
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<EmailAlreadyExistExceptionDTO> EmailAlreadyExistExceptionHandler(EmailAlreadyExistException exception){
        EmailAlreadyExistExceptionDTO dto=new EmailAlreadyExistExceptionDTO();
        dto.setMessage("Email: "+exception.getEmail()+" already exist");
        ResponseEntity<EmailAlreadyExistExceptionDTO> response=new ResponseEntity<>(dto,HttpStatus.BAD_REQUEST);
        return response;
    }
}
