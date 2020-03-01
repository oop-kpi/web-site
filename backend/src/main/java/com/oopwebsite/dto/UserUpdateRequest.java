package com.oopwebsite.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserUpdateRequest {
    @JsonIgnore
    private String prevLogin;
    private String name;
    private String login;
    private String group;
    private String email;
    private String password;
    private String ball;
    private String[] roles;

    public UserUpdateRequest(String login,String name, String group, String password, String ball, String[] roles) {
        this.name = name;
        this.login = login;
        this.group = group;
        this.password = password;
        this.ball = ball;
        this.roles = roles;
    }

    public String getPrevLogin() {
        return prevLogin;
    }

    public void setPrevLogin(String prevLogin) {
        this.prevLogin = prevLogin;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBall() {
        return ball;
    }

    public void setBall(String ball) {
        this.ball = ball;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }
}
