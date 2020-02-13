package com.oopwebsite.dto;

public class UserUpdateRequest {
    private String id;
    private String name;
    private String group;
    private String email;
    private String password;
    private String ball;
    private String[] roles;

    public UserUpdateRequest(String name, String group, String password, String ball, String[] roles) {
        this.name = name;
        this.group = group;
        this.password = password;
        this.ball = ball;
        this.roles = roles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
