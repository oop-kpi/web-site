package com.oopwebsite.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

public class LaboratoryWorkUpdateDto {
    private String id;
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

    public LaboratoryWorkUpdateDto(String id,String name, MultipartFile file, String link) {
        this.id = id;
        this.name = name;
        this.file = file;
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

}
