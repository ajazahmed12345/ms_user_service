package com.ajaz.userservice.controllers;

import com.ajaz.userservice.dtos.UserDto;
import com.ajaz.userservice.exceptions.ApiResponse;
import com.ajaz.userservice.exceptions.UserNotFoundException;
import com.ajaz.userservice.models.User;
import com.ajaz.userservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${base.url}")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto){
        User user = UserDto.from(userDto);
        User savedUser = userService.createUser(user);

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) throws UserNotFoundException {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUserByUserId(@PathVariable("userId") Long userId, @RequestBody UserDto userDto) throws UserNotFoundException {
        User updatedUser = userService.updateUserByUserId(userId, userDto);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<ApiResponse> deleteUserById(@PathVariable("userId") Long userId) throws UserNotFoundException{
        return new ResponseEntity<>(
          userService.deleteUserById(userId),
          HttpStatus.OK
        );
    }

    @GetMapping("/emails/{regex}")
    public List<User> getUsersWithEmailEnding(@PathVariable("regex") String regex){
        return userService.getUsersWithEmailEnding(regex);
    }



}
