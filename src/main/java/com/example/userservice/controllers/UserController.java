package com.example.userservice.controllers;

import com.example.userservice.DTOs.LogInDTO;
import com.example.userservice.DTOs.LogOutDTO;
import com.example.userservice.DTOs.SignUpDTO;
import com.example.userservice.DTOs.UserDTO;
import com.example.userservice.exceptions.EmailAlreadyExistException;
import com.example.userservice.exceptions.EmailDoesNotExistException;
import com.example.userservice.exceptions.ExcededCountOfDevicesException;
import com.example.userservice.exceptions.IncorrectPasswordException;
import com.example.userservice.models.Token;
import com.example.userservice.models.User;
import com.example.userservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
    public ResponseEntity<String> logIn(@RequestBody LogInDTO logInDTO)
            throws IncorrectPasswordException, EmailDoesNotExistException, ExcededCountOfDevicesException {
        Token token=userService.logIn(logInDTO.getEmail(), logInDTO.getPassword());
        ResponseEntity<String> response=new ResponseEntity<>(token.getValue(), HttpStatus.OK);
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

    @PostMapping ("/logout")
    public ResponseEntity<String> logOut(@RequestBody LogOutDTO dto){
        int response=userService.logOut(dto.getToken());
        ResponseEntity<String> responseEntity;
        if(response==0){
            return new ResponseEntity<>("Failed",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Done",HttpStatus.OK);
    }

    @GetMapping("/validate/{token}")
    public ResponseEntity<UserDTO> validate(@PathVariable("token") String tokenValue){
        User user=userService.validate(tokenValue);
        ResponseEntity<UserDTO> responseEntity;
        if(user==null){
            return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
        }
        UserDTO userDTO=UserDTO.from(user);
        return new ResponseEntity<>(userDTO,HttpStatus.OK);
    }
}
