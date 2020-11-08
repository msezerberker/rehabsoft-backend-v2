package com.hacettepe.rehabsoft.controller;

import com.hacettepe.rehabsoft.dto.GeneralEvaluationFormDto;
import com.hacettepe.rehabsoft.dto.PatientDto;
import com.hacettepe.rehabsoft.service.PatientService;
import com.hacettepe.rehabsoft.util.ApiPaths;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping(ApiPaths.PatientFormPath.CTRL)
@RestController
@Api(value = "/api/patient")
public class PatientController {


    @Autowired
    PatientService patientService;

    @RequestMapping(value="/create", method = RequestMethod.POST)
    public boolean savePatient(@RequestBody PatientDto patientDto){
        log.warn("Patient Controllerına girdi");

        boolean isAlreadySaved = patientService.isAlreadySaved(patientDto.getTcKimlikNo());
        if(isAlreadySaved){
            //zaten kayıtlıysa tekrar save etmesini engelledik
            return false;
        }

        patientService.savePatient(patientDto);
        return true;

    }



}
