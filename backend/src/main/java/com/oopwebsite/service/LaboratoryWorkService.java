package com.oopwebsite.service;

import com.oopwebsite.dto.LaboratoryWorkDto;
import com.oopwebsite.entity.LaboratoryWork;

public interface LaboratoryWorkService {
    LaboratoryWork saveLaboratory(LaboratoryWorkDto laboratoryWorkDto);
    void evaluate(LaboratoryWork laboratoryWork);
    LaboratoryWork createLaboratory(LaboratoryWorkDto laboratoryWorkDto);
}
