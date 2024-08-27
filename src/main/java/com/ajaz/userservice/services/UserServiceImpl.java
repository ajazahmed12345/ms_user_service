package com.ajaz.userservice.services;

import com.ajaz.userservice.dtos.UserDto;
import com.ajaz.userservice.models.ApiResponse;
import com.ajaz.userservice.exceptions.UserNotFoundException;
import com.ajaz.userservice.models.User;
import com.ajaz.userservice.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
@Slf4j
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) throws UserNotFoundException{
        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isEmpty()){
            log.info("User with the id: " + id + " does not exist");
            throw new UserNotFoundException("User with id: " + id + " not found in Database.");
        }
        log.info("User with the id: " + id + " exists: {}", userOptional.get());
        return userOptional.get();
    }

    @Override
    public User updateUserByUserId(Long userId, UserDto userDto) throws UserNotFoundException {

        Optional<User>  userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            log.info("User with the id: " + userId + " does not exist");
            throw new UserNotFoundException("User you are trying to update with id: " + userId + " does not exist.");
        }

        User existingUser = userOptional.get();
        existingUser.setName(checkNullOrEmpty(userDto.getName()) ? existingUser.getName() : userDto.getName());
        existingUser.setEmail(checkNullOrEmpty(userDto.getEmail()) ? existingUser.getEmail() : userDto.getEmail());
        existingUser.setPhoneNumber(checkNullOrEmpty(userDto.getPhoneNumber()) ? existingUser.getPhoneNumber() : userDto.getPhoneNumber());
        existingUser.setAddress(checkNullOrEmpty(userDto.getAddress()) ? existingUser.getAddress() : userDto.getAddress());

        log.info("Updating user with userId: " + userId);
        return userRepository.save(existingUser);

    }

    @Override
    public ApiResponse deleteUserById(Long id) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty()){
            log.info("User with the id: " + id + " does not exist");
            throw new UserNotFoundException("User you are trying to delete with id: " + id + " does not exist.");
        }

        userRepository.deleteById(id);

        ApiResponse response = ApiResponse.builder()
                .message("User with id: " + id + " deleted successfully.")
                .success(true)
                .httpStatus(HttpStatus.OK)
                .build();
        log.info("Deleted the user with userId: " + id + " and ApiResponse is: {}", response);
        return response;

    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getUsersWithEmailEnding(String regex) {
        return userRepository.findAllByEmailContaining(regex);
    }

    public boolean checkNullOrEmpty(String str){
        if(null == str || str.isEmpty())
            return true;

        return false;
    }


}
