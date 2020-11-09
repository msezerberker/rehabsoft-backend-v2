package com.hacettepe.rehabsoft.service;

import com.hacettepe.rehabsoft.dto.GeneralEvaluationFormDto;

public interface GeneralEvaluationFormService {
    GeneralEvaluationFormDto save(GeneralEvaluationFormDto GEFD);

    boolean isGeneralEvaluationFormExist();
}
