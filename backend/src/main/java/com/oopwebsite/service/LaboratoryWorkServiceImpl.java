package com.oopwebsite.service;
import com.oopwebsite.controller.exceptions.NoSuchElementException;
import com.oopwebsite.dto.CommentDto;
import com.oopwebsite.dto.EvaluationDto;
import com.oopwebsite.dto.LaboratoryWorkDto;


import com.oopwebsite.entity.Comment;
import com.oopwebsite.entity.LaboratoryWork;
import com.oopwebsite.entity.User;
import com.oopwebsite.repository.LaboratoryWorkRepository;
import com.oopwebsite.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Service
public class LaboratoryWorkServiceImpl implements LaboratoryWorkService {
    private LaboratoryWorkRepository repository;
    private ModelMapper modelMapper;
    private UserRepository userRepository;
    public LaboratoryWorkServiceImpl(LaboratoryWorkRepository repository, ModelMapper modelMapper, UserRepository userRepository) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public User saveLaboratory(LaboratoryWorkDto laboratoryWorkDto) {
        LaboratoryWork laboratoryWork = new LaboratoryWork();
        laboratoryWork.setName(laboratoryWorkDto.getName());
        if (StringUtils.hasText(laboratoryWorkDto.getLink()))
                 laboratoryWork.setPathToFile(laboratoryWorkDto.getLink());
        User owner = laboratoryWorkDto.getOwner();
        laboratoryWork.setUser(owner);
        laboratoryWork = repository.insert(laboratoryWork);
        owner.getLaboratoryWorks().add(laboratoryWork);
        userRepository.save(owner);
        return owner;
    }

    @Override
    public LaboratoryWork evaluate(EvaluationDto evaluationDto) {
        LaboratoryWork laboratoryWork = repository.findById(evaluationDto.getLabId()).orElseThrow(() -> new NoSuchElementException("Cant find lab with id = " + evaluationDto.getLabId()));
        User user = laboratoryWork.getUser();
        CommentDto commentDto = new CommentDto(evaluationDto.getComment(),evaluationDto.getOwner());
        commentDto.setLabId(evaluationDto.getLabId());
        laboratoryWork = comment(commentDto);
        laboratoryWork.setMark(evaluationDto.getMark());
        user.setBall(updateBall(user));
        repository.save(laboratoryWork);
        userRepository.save(user);
        return laboratoryWork;

    }
    private int updateBall(User user){
        AtomicInteger ball = new AtomicInteger();
        user.getLaboratoryWorks().forEach(laboratoryWork -> {
            ball.addAndGet(laboratoryWork.getMark());
        });
        return ball.get();
    }

    @Override
    public LaboratoryWork comment(CommentDto commentDto) {
        Comment comment = new Comment();
        LaboratoryWork laboratoryWork = repository.findById(commentDto.getLabId()).orElseThrow(() -> new NoSuchElementException("Cant find laboratory work with id = " + commentDto.getLabId()));
    //    comment.setOwner(commentDto.getOwner());
        comment.setContent(commentDto.getContent());
        laboratoryWork.getComments().add(comment);
        repository.save(laboratoryWork);
        return laboratoryWork;
    }

    @Override
    public Collection<LaboratoryWork> getLabsToEvaluate() {
        return repository.findAll().stream().filter(e -> e.getMark()<=0).collect(Collectors.toList());
    }

    private LaboratoryWork mapToDao(LaboratoryWorkDto laboratoryWorkDto){
        LaboratoryWork laboratoryWork = modelMapper.map(laboratoryWorkDto, LaboratoryWork.class);
        if (laboratoryWorkDto.getLink()!=null){
            laboratoryWork.setPathToFile(laboratoryWorkDto.getLink());
        }
        //TODO Uploading requests with files to static file storage
        return laboratoryWork;
    }
}
