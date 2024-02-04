package com.squad8.s8orangebackend.dtos;

import com.squad8.s8orangebackend.domain.user.User;

public class UserUpdateDto {
    private String country;

    public UserUpdateDto() {
    }

    public UserUpdateDto(User user) {
        this.country = user.getCountry();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
