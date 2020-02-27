package com.oopwebsite.service;
import com.oopwebsite.controller.exceptions.FileStorageException;
import com.oopwebsite.controller.exceptions.NoSuchElementException;
import com.oopwebsite.dto.*;


import com.oopwebsite.entity.*;
import com.oopwebsite.repository.LaboratoryWorkRepository;
import com.oopwebsite.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Service
public class LaboratoryWorkServiceImpl implements LaboratoryWorkService {
    private LaboratoryWorkRepository repository;
    private ModelMapper modelMapper;
    private UserRepository userRepository;
    private FileStorageService fileStorageService;
    public LaboratoryWorkServiceImpl(LaboratoryWorkRepository repository, ModelMapper modelMapper, UserRepository userRepository,FileStorageService fileStorageService) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public User saveLaboratory(LaboratoryUploadWorkDto laboratoryUploadWorkDto) {
        LaboratoryWork laboratoryWork = new LaboratoryWork();
        laboratoryWork.setName(laboratoryUploadWorkDto.getName());
        if (StringUtils.hasText(laboratoryUploadWorkDto.getLink())) {
            laboratoryWork.setPathToFile(laboratoryUploadWorkDto.getLink());
        }else{
            if (laboratoryUploadWorkDto.getFile()!=null) {
                try {
                    laboratoryWork.setPathToFile(fileStorageService.uploadFile("/"+ UUID.randomUUID()+"."+ laboratoryUploadWorkDto.getFile().getOriginalFilename(), laboratoryUploadWorkDto.getFile().getInputStream()).getPathDisplay());
                } catch (IOException e) {
                    throw new FileStorageException("Error during saving file");
                }
            }
            else  throw new FileStorageException("File is null!");
        }
        User owner = laboratoryUploadWorkDto.getOwner();
        laboratoryWork.setUser(owner);
        laboratoryWork = repository.insert(laboratoryWork);
        owner.getLaboratoryWorks().add(laboratoryWork);
        userRepository.save(owner);
        return owner;
    }

    @Override
    public LaboratoryWork updateLaboratory(LaboratoryWorkUpdateDto laboratoryWorkUpdateDto) {
        LaboratoryWork laboratoryWork = mapToDao(laboratoryWorkUpdateDto);
        repository.save(laboratoryWork);
        return laboratoryWork;
    }

    private LaboratoryWork mapToDao(LaboratoryWorkUpdateDto updateRequest){
        LaboratoryWork prev = repository.findById(updateRequest.getId()).orElseThrow(()->new NoSuchElementException("Cant find lab with id = "+updateRequest.getId()));
        LaboratoryWork lab = new LaboratoryWork();
        lab.setId(prev.getId());
        lab.setUser(prev.getUser());
        lab.setName(StringUtils.isEmpty(updateRequest.getName())? prev.getName(): updateRequest.getName());
        if (StringUtils.hasText(updateRequest.getLink())) {
            lab.setPathToFile(updateRequest.getLink());
        }else{
            if (updateRequest.getFile()!=null) {
                try {
                    lab.setPathToFile(fileStorageService.uploadFile("/"+ UUID.randomUUID()+"."+ updateRequest.getFile().getOriginalFilename(), updateRequest.getFile().getInputStream()).getPathDisplay());
                } catch (IOException e) {
                    throw new FileStorageException("Error during saving file");
                }
            }
            else  throw new FileStorageException("File is null!");
        }
        return lab;
    }
    @Override
    public LaboratoryWork evaluate(EvaluationDto evaluationDto) {
        evaluationDto.getOwner().getLaboratoryWorks();
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
       comment.setOwner(commentDto.getOwner().getName());
        comment.setContent(commentDto.getContent());
        laboratoryWork.getComments().add(comment);
        repository.save(laboratoryWork);
        return laboratoryWork;
    }

    @Override
    public Collection<LaboratoryWork> getLabsToEvaluate() {
        return repository.findAll().stream().filter(e -> e.getMark()<=0).collect(Collectors.toList());
    }

    @Override
    public FileDto getDownloadLink(String labId) {
        return fileStorageService.getDownloadLink(repository.findById(labId).orElseThrow(() -> new NoSuchElementException("Cant find lecture with id = "+labId)).getPathToFile());
    }

    private LaboratoryWork mapToDao(LaboratoryUploadWorkDto laboratoryUploadWorkDto){
        LaboratoryWork laboratoryWork = modelMapper.map(laboratoryUploadWorkDto, LaboratoryWork.class);
        if (laboratoryUploadWorkDto.getLink()!=null){
            laboratoryWork.setPathToFile(laboratoryUploadWorkDto.getLink());
        }
        //TODO Uploading requests with files to static file storage
        return laboratoryWork;
    }
}
