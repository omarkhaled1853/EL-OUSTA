package com.ELOUSTA.ELOUSTA.backend.service.auth;

import com.ELOUSTA.ELOUSTA.backend.entity.TechnicianEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TechnicianAuthenticationDetails implements UserDetails {
    private String password;
    private String username;
    private List<GrantedAuthority> authorities;

    public TechnicianAuthenticationDetails(TechnicianEntity technicianEntity){
        this.username = technicianEntity.getUsername();
        this.password = technicianEntity.getPassword();
        this.authorities = List.of(technicianEntity.getRoles().split(", "))
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
