package com.oopwebsite.service;

import com.oopwebsite.controller.exceptions.FileStorageException;
import com.oopwebsite.dto.LectureUploadRequest;
import com.oopwebsite.entity.Lecture;
import com.oopwebsite.entity.User;
import com.oopwebsite.repository.LectureRepository;
import com.oopwebsite.utility.FileUtility;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class LectureServiceImpl implements LectureService {
    private static Logger LOGGER = LoggerFactory.getLogger(LectureServiceImpl.class);
    @Value("${app.upload.path}")
    private String fileUploadingPath;
    @Autowired
    private LectureRepository lectureRepository;


    @Override
    public void save(LectureUploadRequest request,User user) {
        Lecture lecture = new Lecture();
        lecture.setName(request.getName());
        lecture.setCreator(user);
        lecture.setPath(FileUtility.saveFileToDir(request.getPresentation(),fileUploadingPath));
        lectureRepository.save(lecture);
    }

}
