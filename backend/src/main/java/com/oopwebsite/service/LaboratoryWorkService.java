package com.oopwebsite.service;

import com.oopwebsite.dto.CommentDto;
import com.oopwebsite.dto.EvaluationDto;
import com.oopwebsite.dto.FileDto;
import com.oopwebsite.dto.LaboratoryWorkDto;
import com.oopwebsite.entity.LaboratoryWork;
import com.oopwebsite.entity.User;

import java.util.Collection;

public interface LaboratoryWorkService {
    User saveLaboratory(LaboratoryWorkDto laboratoryWorkDto);
    LaboratoryWork evaluate(EvaluationDto laboratoryWork);
    LaboratoryWork comment(CommentDto commentDto);
    Collection<LaboratoryWork> getLabsToEvaluate();
    FileDto getDownloadLink(String lectureId);
}
