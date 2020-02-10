package com.oopwebsite.dto;

import com.oopwebsite.entity.Group;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
    private String login;
    private String name;
    private String email;
    private String password;
    private String group;
}
