package com.userservice.services;

import com.userservice.entities.User;

import java.util.List;

public interface UserService {

    //create user
    User saveUser(User user);

    //get all user
    List<User> getAll();

    //get single user of given userId

    User getSingleUser(String userId);

    //TODO: delete
    //TODO:update
}
