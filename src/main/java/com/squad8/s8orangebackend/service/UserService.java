package com.squad8.s8orangebackend.service;

import com.squad8.s8orangebackend.domain.user.User;
import com.squad8.s8orangebackend.dtos.UserRegistrationDto;
import com.squad8.s8orangebackend.repository.UserRepository;
import com.squad8.s8orangebackend.service.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User insertUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> findAllUser() {
        return userRepository.findAll();
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

    public void updatePartialUser(Long id, Map<String, Object> fields) {
        User user = userRepository.getReferenceById(id);

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
