package com.oopwebsite.controller;

import com.oopwebsite.dto.LectureUploadRequest;
import com.oopwebsite.dto.PresentationDto;
import com.oopwebsite.entity.Lecture;
import com.oopwebsite.repository.LectureRepository;
import com.oopwebsite.repository.UserRepository;
import com.oopwebsite.service.LectureService;
import com.oopwebsite.wrappers.UserWrapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;


@RestController
@RequestMapping("/api/lecture/")
public class LectureController {
    private LectureService lectureService;

    public LectureController(LectureRepository lectureRepository, LectureService lectureService, UserRepository userRepository) {
        this.lectureService = lectureService;
    }

    @PostMapping(value = "upload",consumes = "multipart/form-data")
    @Secured("ROLE_TEACHER")
    @ApiOperation(value = "Lecture uploading request", response = Void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully "),
            @ApiResponse(code = 401, message = "Unauthorized/ dont have permission"),
            @ApiResponse(code=400, message = "Bad request")})
    public ResponseEntity<?> createNewLecture(@RequestParam(value = "presentation",required = false) MultipartFile multipartFile,@RequestParam("description") String description,@RequestParam("name") String name){
        // Optional.get() since @AuthenticationPrincipal can`t bound to user with wrong login
        lectureService.save(new LectureUploadRequest(name,description,multipartFile));
        return ResponseEntity.ok().body("Lecture successfully uploaded!");
    }
    @GetMapping("getAll")
    @ApiOperation(value = "Get all lecutres", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully "),
            @ApiResponse(code = 401, message = "Unauthorized")})
    public List<Lecture> getAllLectures(){
        return lectureService.getAllLectures();
    }

    @GetMapping("/download/{id}")
    @ApiOperation(value = "Get all lecutres", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully "),
            @ApiResponse(code = 401, message = "Unauthorized")})
    public ResponseEntity<Resource> downloadPresentation(@PathVariable("id") String lectureId) throws IOException {
        PresentationDto downloadLink = lectureService.getDownloadLink(lectureId);
        InputStreamResource resource = new InputStreamResource(new URL(downloadLink.getLink()).openStream());
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Disposition", "attachment; filename="+downloadLink.getFileName());
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        return ResponseEntity.ok().headers(header).contentLength(downloadLink.getFileLength()).contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
    }

}
