package com.oopwebsite.service;

import com.oopwebsite.dto.*;
import com.oopwebsite.entity.LaboratoryWork;
import com.oopwebsite.entity.User;

import java.util.Collection;

public interface LaboratoryWorkService {
    User saveLaboratory(LaboratoryUploadWorkDto laboratoryUploadWorkDto);
    LaboratoryWork updateLaboratory(LaboratoryWorkUpdateDto laboratoryWorkUpdateDto);
    LaboratoryWork evaluate(EvaluationDto laboratoryWork);
    LaboratoryWork comment(CommentDto commentDto);
    Collection<LaboratoryWork> getLabsToEvaluate();
    FileDto getDownloadLink(String lectureId);
}
