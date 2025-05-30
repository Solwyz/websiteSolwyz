package com.solwyz.config;






import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.solwyz.entity.User;

import java.util.Collection;
import java.util.Collections;

public class CurrentUser implements UserDetails {

    private final Long id;
    private final String email;
    private final String password;

    // Add roles if needed
    private final Collection<? extends GrantedAuthority> authorities;

    public CurrentUser(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.authorities = Collections.emptyList(); // You can add roles here if needed
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return email; // Spring Security uses this as login username
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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

	public CurrentUser(Long id, String email, String password, Collection<? extends GrantedAuthority> authorities) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}
    
}
