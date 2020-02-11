package com.oopwebsite.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oopwebsite.entity.User;
import org.springframework.web.multipart.MultipartFile;

public class LaboratoryWorkDto {
    private String labId;
    @JsonIgnore
    private MultipartFile file;
    private String link;
    private User owner;

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public LaboratoryWorkDto(String labId, MultipartFile file, String link, User owner) {
        this.labId = labId;
        this.file = file;
        this.link = link;
        this.owner = owner;
    }

    public String getName() {
        return labId;
    }

    public void setName(String name) {
        this.labId = name;
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
