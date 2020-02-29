package com.oopwebsite.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oopwebsite.entity.User;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

public class LaboratoryUploadWorkDto {
    @JsonIgnore
    private MultipartFile file;
    private String link;
    private String name;

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
