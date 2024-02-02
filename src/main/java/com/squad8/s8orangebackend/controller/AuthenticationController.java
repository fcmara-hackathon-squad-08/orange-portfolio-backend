package com.squad8.s8orangebackend.controller;

import com.squad8.s8orangebackend.domain.user.MyUserPrincipal;
import com.squad8.s8orangebackend.dtos.AuthenticationDto;
import com.squad8.s8orangebackend.dtos.LoginResponseDto;
import com.squad8.s8orangebackend.infra.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Operation(summary = "Login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success login"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content) })
    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid AuthenticationDto data) {
        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(data.getLogin(),
                data.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        var token = tokenService.generateToken((MyUserPrincipal) authentication.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDto(token));
    }

}
