package com.oopwebsite.controller;

import com.oopwebsite.dto.LectureUploadRequest;
import com.oopwebsite.entity.Lecture;
import com.oopwebsite.repository.LectureRepository;
import com.oopwebsite.repository.UserRepository;
import com.oopwebsite.service.LectureService;
import com.oopwebsite.wrappers.UserWrapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/api/lecture/")
public class LectureController {
    private LectureRepository lectureRepository;
    private LectureService lectureService;
    private UserRepository userRepository;

    public LectureController(LectureRepository lectureRepository, LectureService lectureService, UserRepository userRepository) {
        this.lectureRepository = lectureRepository;
        this.lectureService = lectureService;
        this.userRepository = userRepository;
    }

    @PostMapping(value = "upload",consumes = "multipart/form-data")
    @Secured("ROLE_TEACHER")
    @ApiOperation(value = "Lecture uploading request", response = Void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully "),
            @ApiResponse(code = 401, message = "Unauthorized/ dont have permission"),
            @ApiResponse(code=400, message = "Bad request")})
    public ResponseEntity<?> createNewLecture(@RequestParam(value = "presentation",required = false) MultipartFile multipartFile,@RequestParam("name") String name){
        // Optional.get() since @AuthenticationPrincipal can`t bound to user with wrong login
        lectureService.save(new LectureUploadRequest(name,multipartFile));
        return ResponseEntity.ok().body("Lecture successfully uploaded!");
    }
    @GetMapping("getAll")
    @ApiOperation(value = "Get all lecutres", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully "),
            @ApiResponse(code = 401, message = "Unauthorized")})
    public List<Lecture> getAllLectures(){
        return lectureRepository.findAll();
    }

}
