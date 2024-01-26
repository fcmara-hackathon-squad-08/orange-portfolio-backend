package com.squad8.s8orangebackend.service;

import com.squad8.s8orangebackend.domain.user.User;
import com.squad8.s8orangebackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void CreateAUser() {

        when(userRepository.save(any(User.class))).thenReturn(commonUser());

        User user =  userService.insertUser(commonUser());

        verify(userRepository, times(1)).save(any());

        assertNotNull(user.getId());
        assertEquals("name", user.getName());
        assertEquals("lastname", user.getSurname());
        assertEquals("email@gmail.com", user.getEmail());

    }

    public User commonUser() {
        return new User(1L, "name", "lastname", "email@gmail.com", "password", "Brazil", "image");
    }

}
