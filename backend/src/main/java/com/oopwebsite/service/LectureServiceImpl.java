package com.oopwebsite.service;

import com.oopwebsite.controller.exceptions.FileStorageException;
import com.oopwebsite.controller.exceptions.NoSuchElementException;
import com.oopwebsite.dto.LectureUploadRequest;
import com.oopwebsite.dto.FileDto;
import com.oopwebsite.entity.Lecture;
import com.oopwebsite.repository.LectureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Service
public class LectureServiceImpl implements LectureService {
    private static Logger LOGGER = LoggerFactory.getLogger(LectureServiceImpl.class);

    @Autowired
    private LectureRepository lectureRepository;
    @Autowired
    private FileStorageService fileStorageService;


    @Override
    public void save(LectureUploadRequest request) {
        Lecture lecture = new Lecture();
        lecture.setName(request.getName());
        lecture.setDescription(request.getDescription());
        try {
            lecture.setPath(fileStorageService.uploadFile("/"+UUID.randomUUID()+"."+request.getPresentation().getOriginalFilename(),request.getPresentation().getInputStream()).getPathDisplay());
        } catch (IOException e) {
            throw new FileStorageException("Error during file uploading!");
        }
        lectureRepository.save(lecture);
    }

    @Override
    public List<Lecture> getAllLectures() {
        return lectureRepository.findAll();
    }

    @Override
    public FileDto getDownloadLink(String id) {
       return fileStorageService.getDownloadLink(lectureRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Cant find lecture with id = "+id)).getPath());
    }
}
