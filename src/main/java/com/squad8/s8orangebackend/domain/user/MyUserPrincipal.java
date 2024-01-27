package com.squad8.s8orangebackend.domain.user;

import com.squad8.s8orangebackend.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.List;

public class MyUserPrincipal implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1l;

    @Autowired
    private User user;

    public MyUserPrincipal(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.user.getUserRole() == UserRole.ADMIN_ROLE) return List.of(new SimpleGrantedAuthority("ADMIN_ROLE"),
                new SimpleGrantedAuthority("USER_ROLE"));
        else return List.of(new SimpleGrantedAuthority("USER_ROLE"));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
