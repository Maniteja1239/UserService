package com.example.userservice.service;

import com.example.userservice.exceptions.EmailAlreadyExistException;
import com.example.userservice.exceptions.EmailDoesNotExistException;
import com.example.userservice.exceptions.IncorrectEmailOrPasswordException;
import com.example.userservice.models.User;
import com.example.userservice.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;

    public UserService(UserRepository userRepository,BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository=userRepository;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
    }

    public User signUp(String name,String email,String password) throws EmailAlreadyExistException{
        Optional<User> optional=userRepository.findByEmail(email);
        if(!optional.isEmpty()) {
            throw new EmailAlreadyExistException(email);
        }
        User user=new User();
        user.setName(name);
        user.setEmail(email);
        String encodedPassword= bCryptPasswordEncoder.encode(password);
        user.setHashedPassword(encodedPassword);
        user.setIsEmailVerified(true);
        return userRepository.save(user);
    }

    public User logIn(String email,String password) throws EmailDoesNotExistException, IncorrectEmailOrPasswordException {
        Optional<User> optional=userRepository.findByEmail(email);
        if(optional.isEmpty()){
            throw new EmailDoesNotExistException(email);
        }
        User user=optional.get();
        String encodedPassword=user.getHashedPassword();
        if(!bCryptPasswordEncoder.matches(password,encodedPassword)){
            throw new IncorrectEmailOrPasswordException(email,password);
        }
        return user;
    }
/*
    public User getUserById(Long id){
        Optional<User> optional= userRepository.findById(id);
        if(optional.isEmpty()){
            throw new UserNotFoundException();
        }
        return optional.get();
    }
*/
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void logOut(){

    }

}
