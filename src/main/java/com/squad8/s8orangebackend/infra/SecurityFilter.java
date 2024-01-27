package com.squad8.s8orangebackend.infra;

import com.squad8.s8orangebackend.domain.user.MyUserPrincipal;
import com.squad8.s8orangebackend.domain.user.User;
import com.squad8.s8orangebackend.infra.security.TokenService;
import com.squad8.s8orangebackend.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoveryToken(request);
        if (token != null) {
            var login = tokenService.verifyToken(token);
            User user = userRepository.findByEmail(login);
            MyUserPrincipal userPrincipal = new MyUserPrincipal(user);

            var authentication = new UsernamePasswordAuthenticationToken(userPrincipal, null,
                    userPrincipal.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

        }
        filterChain.doFilter(request, response);
    }

    private String recoveryToken(HttpServletRequest request) {

        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        else return authHeader.replace("Bearer ", "");
    }
}
