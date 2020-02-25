package com.oopwebsite.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.oopwebsite.entity.User;

public class CommentDto {
    private String labId;
    private String content;
    @JsonIgnore
    private User owner;

    public CommentDto(String content, User owner) {
        this.content = content;
        this.owner = owner;
    }

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
