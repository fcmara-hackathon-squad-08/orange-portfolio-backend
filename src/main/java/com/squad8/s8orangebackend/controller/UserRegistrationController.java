package com.squad8.s8orangebackend.controller;

import com.squad8.s8orangebackend.domain.user.User;
import com.squad8.s8orangebackend.dtos.UserRegistrationDto;
import com.squad8.s8orangebackend.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/user")
public class UserRegistrationController {

    @Autowired
    private UserService userService;

    @Transactional
    @PostMapping("/register")
    public ResponseEntity<User> insertUser(@RequestBody @Validated UserRegistrationDto userRegistrationDTO, UriComponentsBuilder uriComponentsBuilder) {
        User user = userService.fromDto(userRegistrationDTO);
        user = userService.insertUser(user);
        URI uri = uriComponentsBuilder.path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(user);
    }
}
