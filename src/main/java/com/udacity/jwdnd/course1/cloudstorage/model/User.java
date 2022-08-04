package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Data;

@Data
public class User {
    private Integer userid;
    private String username;
    private String salt;
    private String password;
    private String firstname;
    private String lastname;

    /**
     * @param userId
     * @param username
     * @param salt
     * @param password
     * @param firstName
     * @param lastName
     */
    public User(Integer userId, String username, String salt, String password, String firstname, String lastname) {
        this.userid = userId;
        this.username = username;
        this.salt = salt;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }
}
