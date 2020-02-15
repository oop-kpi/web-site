package com.oopwebsite.service;

import com.oopwebsite.dto.CommentDto;
import com.oopwebsite.dto.EvaluationDto;
import com.oopwebsite.dto.LaboratoryWorkDto;
import com.oopwebsite.entity.LaboratoryWork;
import com.oopwebsite.entity.User;

public interface LaboratoryWorkService {
    User saveLaboratory(LaboratoryWorkDto laboratoryWorkDto);
    LaboratoryWork evaluate(EvaluationDto laboratoryWork);
    LaboratoryWork comment(CommentDto commentDto);
}
