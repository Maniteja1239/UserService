package com.example.userservice.service;

import com.example.userservice.DTOs.UserDTO;
import com.example.userservice.exceptions.EmailAlreadyExistException;
import com.example.userservice.exceptions.EmailDoesNotExistException;
import com.example.userservice.exceptions.ExcededCountOfDevicesException;
import com.example.userservice.exceptions.IncorrectPasswordException;
import com.example.userservice.models.Token;
import com.example.userservice.models.User;
import com.example.userservice.repository.TokenRepository;
import com.example.userservice.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;
    private TokenRepository tokenRepository;

    public UserService(UserRepository userRepository,BCryptPasswordEncoder bCryptPasswordEncoder,TokenRepository tokenRepository){
        this.userRepository=userRepository;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
        this.tokenRepository=tokenRepository;
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

    public Token logIn(String email,String password)
            throws EmailDoesNotExistException, IncorrectPasswordException, ExcededCountOfDevicesException {
        Optional<User> optional=userRepository.findByEmail(email);
        if(optional.isEmpty()){
            throw new EmailDoesNotExistException(email);
        }
        User user=optional.get();
        String encodedPassword=user.getHashedPassword();
        if(!bCryptPasswordEncoder.matches(password,encodedPassword)){
            throw new IncorrectPasswordException();
        }
//        Optional<Token> optionalToken=tokenRepository.getAllTokensByUserId(user.getId());
//        if(optionalToken.isEmpty()){
//            return generateToken(user);
//        }
        int count=tokenRepository.getAllTokensByUserId(user.getId()).size();
        if(count>=2){
            throw new ExcededCountOfDevicesException(email);
        }
        return generateToken(user);
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

    public int logOut(String tokenValue){
        Token token=getTokenByValueAndDeletedAndExpiry(tokenValue);
        if(token==null){
            return 0;
        }
        token.setDeleted(true);
        tokenRepository.save(token);
        return 1;
    }

    public Token generateToken(User user){
        LocalDate currentDate=LocalDate.now();
        LocalDate thirtyDaysLater=currentDate.plusDays(30);
        Date expiryDate = Date.from(thirtyDaysLater.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Token token=new Token();
        token.setUser(user);
        token.setExpiryAt(expiryDate);
        token.setValue(RandomStringUtils.randomAlphabetic(128));
        token.setDeleted(false);
        tokenRepository.save(token);
        return token;
    }

    public User validate(String tokenValue){
        Token token=getTokenByValueAndDeletedAndExpiry(tokenValue);
        if(token==null){
            return null;
        }
        User user= token.getUser();
        return user;
    }

    public Token getTokenByValueAndDeletedAndExpiry(String tokenValue){
        Optional<Token> optionalToken=tokenRepository.findTokenByValueAndDeletedAndExpiryAtGreaterThan(tokenValue,false, new Date());
        if(optionalToken.isEmpty()){
            return null;
        }
        return optionalToken.get();
    }
}
