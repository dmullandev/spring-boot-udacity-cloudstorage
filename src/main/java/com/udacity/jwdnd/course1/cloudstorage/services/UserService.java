package com.udacity.jwdnd.course1.cloudstorage.services;

import java.security.SecureRandom;
import java.util.Base64;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;

@Service
public class UserService {
    private static final Logger LOG = LogManager.getLogger(UserService.class);

    private UserMapper userMapper;
    private HashService hashService;

    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    public boolean isUsernameAvailable(String username) {
        return getUser(username) == null;
    }

    public User getUser(String username) {
        return userMapper.getUser(username);
    }

    public Integer getUserid(String username) {
        return userMapper.getUser(username).getUserid();
    }

    public String getUsernameById(Integer userid) {
        return userMapper.getUsernameById(userid);
    }

    public int createUser(User user) {
        LOG.info("Inserting user for username: " + user.getUsername());
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);

        return userMapper.insertUser(new User(null, user.getUsername(), encodedSalt, hashedPassword,
                user.getFirstname(), user.getLastname()));
    }
}
