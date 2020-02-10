package com.oopwebsite.controller;

import com.oopwebsite.dto.LectureUploadRequest;
import com.oopwebsite.entity.Lecture;
import com.oopwebsite.repository.LectureRepository;
import com.oopwebsite.repository.UserRepository;
import com.oopwebsite.service.LectureService;
import com.oopwebsite.wrappers.UserWrapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/lecture/")
@AllArgsConstructor
public class LectureController {
    private LectureRepository lectureRepository;
    private LectureService lectureService;
    private UserRepository userRepository;
    @PostMapping("upload")
    @Secured("ROLE_TEACHER")
    public ResponseEntity<?> createNewLecture(@AuthenticationPrincipal UserWrapper wrapper, @RequestParam("presentation") MultipartFile multipartFile,@RequestParam("name") String name){
        // Optional.get() since @AuthenticationPrincipal can`t bound to user with wrong login
        lectureService.save(new LectureUploadRequest(name,multipartFile),userRepository.findByLogin(wrapper.getLogin()).get());
        return ResponseEntity.ok().body("Lecture successfully uploaded!");
    }
    @GetMapping("getAll")
    public Iterable<Lecture> getAllLectures(){
        return lectureRepository.findAll();
    }

}
