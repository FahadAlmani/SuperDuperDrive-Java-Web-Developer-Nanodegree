package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.dto.SignupForm;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.UserModel;
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

    public int createUser(SignupForm signupForm){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(signupForm.getPassword(), encodedSalt);
        return userMapper.insert(new UserModel(
                signupForm.getUsername(),
                encodedSalt,
                hashedPassword,
                signupForm.getFirstName(),
                signupForm.getLastName()));
    }
}
