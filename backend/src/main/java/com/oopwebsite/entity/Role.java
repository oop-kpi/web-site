package com.oopwebsite.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_USER,ROLE_TEACHER;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
