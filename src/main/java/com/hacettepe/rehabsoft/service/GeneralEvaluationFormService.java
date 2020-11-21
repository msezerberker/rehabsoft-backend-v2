package com.hacettepe.rehabsoft.service;

import com.hacettepe.rehabsoft.dto.GeneralEvaluationFormDto;
import org.springframework.web.multipart.MultipartFile;

public interface GeneralEvaluationFormService {
    Boolean save(String GEFD, MultipartFile botoxImage);

    boolean isGeneralEvaluationFormExist();
}
