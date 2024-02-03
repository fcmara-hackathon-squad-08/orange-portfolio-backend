package com.squad8.s8orangebackend.dtos;

import com.squad8.s8orangebackend.domain.user.User;

public class UserUpdateDto {
    private String imageUrl;
    private String country;

    public UserUpdateDto() {
    }

    public UserUpdateDto(User user) {
        this.imageUrl = user.getImageUrl();
        this.country = user.getCountry();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
