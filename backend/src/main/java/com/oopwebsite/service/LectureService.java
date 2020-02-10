package com.oopwebsite.service;

import com.oopwebsite.dto.LectureUploadRequest;
import com.oopwebsite.entity.User;

public interface LectureService {
    void save(LectureUploadRequest request, User user);
}
