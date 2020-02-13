package com.oopwebsite.dto;


public class SignUpRequest {
    private String login;
    private String name;
    private String email;
    private String password;
    private String group;

    public SignUpRequest(String login, String name, String email, String password, String group) {
        this.login = login;
        this.name = name;
        this.email = email;
        this.password = password;
        this.group = group;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }


}
