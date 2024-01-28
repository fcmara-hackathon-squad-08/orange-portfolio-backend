package com.squad8.s8orangebackend.dtos;

import jakarta.validation.constraints.NotNull;

public class AuthenticationDto {

    @NotNull
    private String login;
    @NotNull
    private String password;

    public AuthenticationDto(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
