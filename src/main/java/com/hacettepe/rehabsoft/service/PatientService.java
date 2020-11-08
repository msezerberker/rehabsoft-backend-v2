package com.hacettepe.rehabsoft.service;

import com.hacettepe.rehabsoft.dto.GeneralEvaluationFormDto;
import com.hacettepe.rehabsoft.dto.PatientDto;
import com.hacettepe.rehabsoft.entity.GeneralEvaluationForm;
import com.hacettepe.rehabsoft.entity.Patient;

public interface PatientService {


    public PatientDto savePatient(PatientDto patientDto);

    public boolean isAlreadySaved(String tcKimlik);

}
