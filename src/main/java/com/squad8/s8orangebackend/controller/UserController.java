package com.squad8.s8orangebackend.controller;

import com.squad8.s8orangebackend.domain.user.User;
import com.squad8.s8orangebackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/me")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        user = userService.updateUserBasicInformation(user);
        return ResponseEntity.ok().body(user);
    }

    @PatchMapping("/partial/me")
    public ResponseEntity<Void> updateUser(@RequestBody Map<String, Object> fields) {
        userService.updatePartialUser(fields);
        return ResponseEntity.ok().build();
    }

}
