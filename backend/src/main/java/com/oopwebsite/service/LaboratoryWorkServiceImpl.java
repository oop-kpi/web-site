package com.oopwebsite.service;
import com.oopwebsite.controller.exceptions.NoSuchElementException;
import com.oopwebsite.dto.LaboratoryWorkDto;


import com.oopwebsite.entity.LaboratoryWork;
import com.oopwebsite.entity.User;
import com.oopwebsite.repository.LaboratoryWorkRepository;
import com.oopwebsite.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


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
    public LaboratoryWork saveLaboratory(LaboratoryWorkDto laboratoryWorkDto) {
        LaboratoryWork laboratoryWork = repository.findById(laboratoryWorkDto.getLabId()).orElseThrow(()->new NoSuchElementException("Cant find lab with id  = "+laboratoryWorkDto.getLabId()));
        User owner = laboratoryWorkDto.getOwner();
        owner.getLaboratoryWorks().add(laboratoryWork);
        userRepository.save(owner);
        return laboratoryWork;
    }

    @Override
    public void evaluate(LaboratoryWork laboratoryWork) {
        repository.save(laboratoryWork);
    }

    @Override
    public LaboratoryWork createLaboratory(LaboratoryWorkDto laboratoryWorkDto) {
        LaboratoryWork s = new LaboratoryWork();
        s.setName(laboratoryWorkDto.getName());
        repository.save(s);
        return s;

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
