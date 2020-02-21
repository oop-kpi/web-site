package com.oopwebsite.dto;


import org.springframework.web.multipart.MultipartFile;

public class LectureUploadRequest {
    public LectureUploadRequest(String name, String description, MultipartFile presentation) {
        this.name = name;
        this.description = description;
        this.presentation = presentation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getPresentation() {
        return presentation;
    }

    public void setPresentation(MultipartFile presentation) {
        this.presentation = presentation;
    }

    private String name;
    private MultipartFile presentation;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
