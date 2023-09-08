package com.hyunbenny.bookroom.auth;

import com.hyunbenny.bookroom.dto.UserAccountDto;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {

    private UserAccountDto userAccountDto;

    // 일반 로그인
    public CustomUserDetails(UserAccountDto userAccountDto) {
        this.userAccountDto = userAccountDto;
    }

    /**
     * UserDetails
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> roles = getGrantedAuthorities(userAccountDto.role());
        return roles;
    }

    @Override
    public String getPassword() {
        return userAccountDto.password();
    }

    @Override
    public String getUsername() {
        return userAccountDto.userId();
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
        return userAccountDto.deletedAt() == null;
    }

    private static List<GrantedAuthority> getGrantedAuthorities(String role) {
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(role));
        return roles;
    }

    @Override
    public String toString() {
        return "CustomUserDetails{" +
                "userAccountDto=" + userAccountDto +
                '}';
    }
}
