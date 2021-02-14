package com.hacettepe.rehabsoft.service;

import com.hacettepe.rehabsoft.dto.GefDto;
import com.hacettepe.rehabsoft.dto.GeneralEvaluationFormDto;
import com.hacettepe.rehabsoft.entity.GeneralEvaluationForm;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface GeneralEvaluationFormService {
    Boolean save(String GEFD, MultipartFile botoxImage, MultipartFile[] epicrisisImages, MultipartFile[] otherOrthesisImages);

    boolean isGeneralEvaluationFormExist();

    GefDto getGefd(String tcKimlikNo);

    byte[] getBotoxImageById(Long id) throws IOException;
}
