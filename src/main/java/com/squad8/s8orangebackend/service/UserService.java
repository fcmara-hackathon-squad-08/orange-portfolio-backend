package com.squad8.s8orangebackend.service;

import com.squad8.s8orangebackend.domain.user.User;
import com.squad8.s8orangebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User insertUser(User user) {
        return userRepository.save(user);
    }
}
