package com.hacettepe.rehabsoft.service;

import com.hacettepe.rehabsoft.dto.GeneralEvaluationFormDto;
import com.hacettepe.rehabsoft.dto.PatientDetailsDto;
import com.hacettepe.rehabsoft.dto.PatientDto;
import com.hacettepe.rehabsoft.dto.UserDto;
import com.hacettepe.rehabsoft.entity.GeneralEvaluationForm;
import com.hacettepe.rehabsoft.entity.Patient;

import java.util.List;

public interface PatientService {


    PatientDto savePatient(PatientDto patientDto);

    Boolean isPatientAlreadySaved();

    Boolean isIdentityNoExists(String tcKimlikNo);
    List<PatientDetailsDto> getAllPatientUsers();


}
