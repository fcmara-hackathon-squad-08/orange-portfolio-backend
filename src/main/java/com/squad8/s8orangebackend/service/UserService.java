package com.squad8.s8orangebackend.service;

import com.squad8.s8orangebackend.domain.user.MyUserPrincipal;
import com.squad8.s8orangebackend.domain.user.User;
import com.squad8.s8orangebackend.dtos.UserRegistrationDto;
import com.squad8.s8orangebackend.dtos.UserUpdateDto;
import com.squad8.s8orangebackend.repository.UserRepository;
import com.squad8.s8orangebackend.service.exceptions.InvalidPropertyValueException;
import com.squad8.s8orangebackend.service.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private S3Service s3Services;

    private static final String URL = "https://s3.amazonaws.com/";

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

    public User updateUserBasicInformation(MultipartFile file) {

        User entity = getCurrentUser();
        try {
            updateUserData(file, entity);
            return userRepository.save(entity);

        } catch (Exception e) {
            throw new ResourceNotFoundException(entity.getId());
        }
    }

    private void updateUserData(MultipartFile file, User entity) {
        String currentFile = URL + s3Services.getBucketName() + entity.getId() + file.getOriginalFilename();


        if (!Objects.equals(entity.getImageUrl(), currentFile) && entity.getImageUrl() != null) {
            s3Services.deleteFile(entity.getImageUrl());
            String img = s3Services.saveFile(entity.getId(), file);
            entity.setImageUrl(img);
        }
        String img = s3Services.saveFile(entity.getId(), file);
        entity.setImageUrl(img);
    }


    public User fromDto(UserRegistrationDto userRegistrationDto) {
        return new User(userRegistrationDto.getName(),
                userRegistrationDto.getSurname(), userRegistrationDto.getEmail(),
                userRegistrationDto.getPassword());
    }

    public User fromUpdateDto(UserUpdateDto userUpdateDto) {
        return new User(userUpdateDto.getCountry());
    }
}
