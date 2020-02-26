package com.oopwebsite.service;

import com.oopwebsite.dto.LectureUploadRequest;
import com.oopwebsite.dto.FileDto;
import com.oopwebsite.entity.Lecture;

import java.util.List;

public interface LectureService {
    void save(LectureUploadRequest request);
    List<Lecture> getAllLectures();
    FileDto getDownloadLink(String id);
}
