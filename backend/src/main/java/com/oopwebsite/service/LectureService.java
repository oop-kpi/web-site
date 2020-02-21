package com.oopwebsite.service;

import com.oopwebsite.dto.LectureUploadRequest;
import com.oopwebsite.dto.PresentationDto;
import com.oopwebsite.entity.Lecture;

import java.util.List;

public interface LectureService {
    void save(LectureUploadRequest request);
    List<Lecture> getAllLectures();
    PresentationDto getDownloadLink(String id);
}
