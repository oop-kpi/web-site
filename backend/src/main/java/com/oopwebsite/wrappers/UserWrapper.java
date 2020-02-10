package com.oopwebsite.wrappers;

import com.oopwebsite.entity.Role;
import com.oopwebsite.entity.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
public class UserWrapper implements UserDetails {

    private int id;
    private String login;
    private String password;
    private String email;
    private String name;
    public UserWrapper(int id, String name, String login, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.email = email;
        this.password = password;
        this.roles = authorities;
    }
    public static UserWrapper create(User user){
        List<GrantedAuthority> authorities = Collections.
                singletonList(Role.ROLE_USER);

        return new UserWrapper(
                user.getId(),
                user.getName(),
                user.getLogin(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }


    private Collection<? extends GrantedAuthority> roles;


    @Override
    public String getUsername() {
        return login;
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
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }





}
