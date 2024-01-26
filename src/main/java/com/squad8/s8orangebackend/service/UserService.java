package com.squad8.s8orangebackend.service;

import com.squad8.s8orangebackend.domain.user.User;
import com.squad8.s8orangebackend.dtos.UserRegistrationDto;
import com.squad8.s8orangebackend.repository.UserRepository;
import com.squad8.s8orangebackend.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User insertUser(User user) {
        return userRepository.save(user);
    }

    public User updateUserBasicInformation(Long id, User user) {
        try {
            User entity = userRepository.getReferenceById(id);
            updateData(entity, user);
            return userRepository.save(entity);
        } catch (Exception e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(User entity, User user) {
        entity.setName(user.getName());
        entity.setSurname(user.getSurname());
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());
        entity.setCountry(user.getCountry());
        entity.setImageUrl(user.getImageUrl());
    }

    public User fromDto(UserRegistrationDto userRegistrationDto) {
        return new User(userRegistrationDto.getName(),
                userRegistrationDto.getSurname(), userRegistrationDto.getEmail(),
                userRegistrationDto.getPassword());
    }
}
