package com.squad8.s8orangebackend.dtos;

import com.squad8.s8orangebackend.domain.user.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class UserRegistrationDto {

    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;

    public UserRegistrationDto() {

    }

    public UserRegistrationDto(User user) {
        this.name = user.getName();
        this.surname = user.getSurname();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

