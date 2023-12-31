package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.UserModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final HashService hashService;

    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    public UserModel getUser(String username){
        return userMapper.getUser(username);
    }
    public boolean isAvailableUsername(String username){
        return userMapper.getUser(username) == null;
    }

    public int createUser(UserModel userModel){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(userModel.getPassword(), encodedSalt);
        return userMapper.insert(new UserModel(null,
                userModel.getUsername(),
                encodedSalt,
                hashedPassword,
                userModel.getFirstName(),
                userModel.getLastName()));
    }
    public int getUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userMapper.getUserId(authentication.getName());
    }
}
