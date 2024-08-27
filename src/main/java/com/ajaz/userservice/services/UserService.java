package com.ajaz.userservice.services;


import com.ajaz.userservice.dtos.UserDto;
import com.ajaz.userservice.exceptions.ApiResponse;
import com.ajaz.userservice.exceptions.UserNotFoundException;
import com.ajaz.userservice.models.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    User getUserById(Long id) throws UserNotFoundException;

    User updateUserByUserId(Long userId, UserDto userDto) throws UserNotFoundException;

    ApiResponse deleteUserById(Long id) throws UserNotFoundException;

    List<User> getAllUsers();

    List<User> getUsersWithEmailEnding(String regex);

}
