package com.oopwebsite.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oopwebsite.entity.User;
import org.springframework.web.multipart.MultipartFile;

public class LaboratoryUploadWorkDto {
    @JsonIgnore
    private MultipartFile file;
    private String link;
    private String name;
    private int ball;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private User owner;

    public LaboratoryUploadWorkDto(String name, MultipartFile file, String link, User owner) {
        this.name = name;
        this.file = file;
        this.link = link;
        this.owner = owner;
    }

    public LaboratoryUploadWorkDto(MultipartFile file, String link, String name, int ball, User owner) {
        this.file = file;
        this.link = link;
        this.name = name;
        this.ball = ball;
        this.owner = owner;
    }

    public int getBall() {
        return ball;
    }

    public void setBall(int ball) {
        this.ball = ball;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
