package com.pinsoft.interns.Service;

import com.pinsoft.interns.DTO.UserUpdateDto;
import com.pinsoft.interns.Entity.User;
import com.pinsoft.interns.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
    public void updateUser(UserUpdateDto userUpdateDto) {
        Optional<User> userOptional = userRepository.findById(userUpdateDto.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (userUpdateDto.getUsername() != null) {
                user.setUsername(userUpdateDto.getUsername());
            }
            if (userUpdateDto.getEmail() != null) {
                user.setEmail(userUpdateDto.getEmail());
            }
            if (userUpdateDto.getPassword() != null) {
                user.setPassword(userUpdateDto.getPassword());
            }
            userRepository.save(user);
        } else {
            throw new EntityNotFoundException("User not found with id: " + userUpdateDto.getId());
        }
    }
}