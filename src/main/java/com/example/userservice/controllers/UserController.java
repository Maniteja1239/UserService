package com.example.userservice.controllers;

import com.example.userservice.DTOs.LogInDTO;
import com.example.userservice.DTOs.SignUpDTO;
import com.example.userservice.DTOs.UserDTO;
import com.example.userservice.exceptions.EmailAlreadyExistException;
import com.example.userservice.exceptions.EmailDoesNotExistException;
import com.example.userservice.exceptions.IncorrectEmailOrPasswordException;
import com.example.userservice.models.User;
import com.example.userservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    UserController(UserService userService){
        this.userService=userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUpDTO signUpDTO) throws EmailAlreadyExistException {
        User user=userService.signUp(signUpDTO.getName(),signUpDTO.getEmail(), signUpDTO.getPassword());
        UserDTO userDTO=UserDTO.from(user);
        ResponseEntity<UserDTO> response=new ResponseEntity<>(userDTO,HttpStatus.OK);
        return response;
    }

    @PostMapping("/login")
    public ResponseEntity<User> logIn(@RequestBody LogInDTO logInDTO) throws IncorrectEmailOrPasswordException, EmailDoesNotExistException {
        User user=userService.logIn(logInDTO.getEmail(), logInDTO.getPassword());
        ResponseEntity<User> response=new ResponseEntity<>(user, HttpStatus.OK);
        return response;
    }
    /*
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id){
        User user=userService.getUserById(id);
        ResponseEntity<User> response=new ResponseEntity<>(user,HttpStatus.OK);
        return response;
    }

    @GetMapping()
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
     */
    public void logOut(){

    }

}
