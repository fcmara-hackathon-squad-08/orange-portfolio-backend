package com.squad8.s8orangebackend.service;

import com.squad8.s8orangebackend.domain.user.MyUserPrincipal;
import com.squad8.s8orangebackend.domain.user.User;
import com.squad8.s8orangebackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private PasswordEncoder encoder;
    @Mock
    private S3Service s3Service;
    @Mock
    private MultipartFile multipartFile;
    @Mock
    private MyUserPrincipal myUserPrincipal;
    @Mock
    private Authentication authentication;

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

        commonUser().setPassword(encoder.encode("password"));
        User user = userService.insertUser(commonUser());

        verify(userRepository, times(1)).save(any());

        assertNotNull(user.getId());
        assertEquals("name", user.getName());
        assertEquals("lastname", user.getSurname());
        assertEquals("email@gmail.com", user.getEmail());

    }

    @Test
    public void shouldGetACurrentUser() {

        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);

        when(authentication.getPrincipal()).thenReturn(myUserPrincipal);

        User user = new User();
        when(myUserPrincipal.user()).thenReturn(user);

        User result = userService.getCurrentUser();

        assertEquals(user, result);
    }


    public User commonUser() {
        return new User(1L, "name", "lastname", "email@gmail.com", "password", "Brazil", "image");
    }

}
