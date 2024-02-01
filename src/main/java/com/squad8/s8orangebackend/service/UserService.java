package com.squad8.s8orangebackend.service;

import com.squad8.s8orangebackend.domain.user.MyUserPrincipal;
import com.squad8.s8orangebackend.domain.user.User;
import com.squad8.s8orangebackend.dtos.UserRegistrationDto;
import com.squad8.s8orangebackend.repository.UserRepository;
import com.squad8.s8orangebackend.service.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null && principal instanceof MyUserPrincipal) {
            User credentials = ((MyUserPrincipal) principal).user();
            return credentials;
        }
        return null;
    }

    public User insertUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUserBasicInformation(User user) {
        try {
            User entity = getCurrentUser();
            updateData(entity, user);
            return userRepository.save(entity);
        } catch (Exception e) {
            throw new ResourceNotFoundException(user.getId());
        }
    }

    public void updatePartialUser(Map<String, Object> fields) {
        User user = userRepository.getReferenceById(getCurrentUser().getId());

        fields.forEach((propertyName, propertyValue) -> {
            if (propertyName.equals("country")) {
                user.setCountry((String) propertyValue);
            }

            if (propertyName.equals("imageUrl")) {
                user.setImageUrl((String) propertyValue);
            }
        });
        userRepository.save(user);
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
