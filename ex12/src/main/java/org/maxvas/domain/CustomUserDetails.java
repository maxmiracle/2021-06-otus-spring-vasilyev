package org.maxvas.domain;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Реализация UserDetails для CustomUserDetailsService.
 */
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final User user;

    // only one ROLE per USER.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return (Collection)List.of(new SimpleGrantedAuthority("ROLE_"+user.getRole()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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