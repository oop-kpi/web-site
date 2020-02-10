package com.oopwebsite.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Data
public class LectureUploadRequest {
    private String name;
    private MultipartFile presentation;

}
