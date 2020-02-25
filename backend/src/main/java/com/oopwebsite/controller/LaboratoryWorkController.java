package com.oopwebsite.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.oopwebsite.controller.exceptions.BadRequestException;
import com.oopwebsite.dto.CommentDto;
import com.oopwebsite.dto.EvaluationDto;
import com.oopwebsite.dto.LaboratoryWorkDto;
import com.oopwebsite.entity.LaboratoryWork;
import com.oopwebsite.entity.User;
import com.oopwebsite.entity.view.View;
import com.oopwebsite.repository.UserRepository;
import com.oopwebsite.service.LaboratoryWorkService;
import com.oopwebsite.wrappers.UserWrapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/lab")
public class LaboratoryWorkController {
    private UserRepository repository;
    private LaboratoryWorkService laboratoryWorkService;

    public LaboratoryWorkController(UserRepository repository,LaboratoryWorkService service) {
        this.repository = repository;
        this.laboratoryWorkService = service;
    }

    @PostMapping("create")
    @JsonView(View.LABORATORY_WORK.class)
    @ApiOperation(value = "Create lab", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully "),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code=400, message = "Bad request")})
    public User createLaboratoryWork(@RequestParam(value = "file",required = false) MultipartFile multipartFile,
                                     @RequestParam("name") String name,
                                     @RequestParam(value = "link",required = false) String link,
                                     @AuthenticationPrincipal UserWrapper user){
        if (!StringUtils.hasText(link)&&multipartFile==null) throw new BadRequestException("File and link is empty!");
        LaboratoryWorkDto laboratoryWorkDto = new LaboratoryWorkDto(name,multipartFile,link,repository.findById(user.getId()).get());
        return laboratoryWorkService.saveLaboratory(laboratoryWorkDto);

    }
    @PostMapping("comment")
    @Secured({"ROLE_REVIEWER","ROLE_TEACHER"})
    @JsonView(View.EVALUATION.class)
    @ApiOperation(value = "Comment lab", response = LaboratoryWork.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully "),
            @ApiResponse(code = 401, message = "Unauthorized/ dont have permission"),
            @ApiResponse(code=400, message = "Bad request")})
    public LaboratoryWork comment(@RequestBody CommentDto commentDto, @AuthenticationPrincipal UserWrapper user){
        commentDto.setOwner(repository.findById(user.getId()).get());
        return laboratoryWorkService.comment(commentDto);

    }
    @PostMapping("evaluate")
    @Secured("ROLE_TEACHER")
    @JsonView(View.LABORATORY_WORK.class)
    @ApiOperation(value = "Evaluate lab", response = LaboratoryWork.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully "),
            @ApiResponse(code = 401, message = "Unauthorized/ dont have permission"),
            @ApiResponse(code=400, message = "Bad request")})
    public LaboratoryWork evaluate(@RequestBody EvaluationDto evaluationDto, @AuthenticationPrincipal UserWrapper user){
        evaluationDto.setOwner(repository.findById(user.getId()).get());
        return laboratoryWorkService.evaluate(evaluationDto);

    }
    @GetMapping("getLabsToEvaluate")
    @Secured({"ROLE_TEACHER","ROLE_REVIEWER"})
    @JsonView(View.EVALUATION.class)
    @ApiOperation(value = "Get labs for evaluation", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully "),
            @ApiResponse(code = 401, message = "Unauthorized/ dont have permission")})
    public Collection<LaboratoryWork> getLabsToEvaluate(){
        return laboratoryWorkService.getLabsToEvaluate();

    }
}
