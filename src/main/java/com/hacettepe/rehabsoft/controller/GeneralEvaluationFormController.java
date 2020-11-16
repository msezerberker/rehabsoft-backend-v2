package com.hacettepe.rehabsoft.controller;

import com.hacettepe.rehabsoft.dto.GeneralEvaluationFormDto;
import com.hacettepe.rehabsoft.service.GeneralEvaluationFormService;
import com.hacettepe.rehabsoft.util.ApiPaths;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Slf4j
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping(ApiPaths.GeneralEvaluationFormPath.CTRL)
@RestController
@Api(value = "/api/patient/generalevaluationform")
public class GeneralEvaluationFormController {

    private final GeneralEvaluationFormService generalEvaluationFormService;

    public GeneralEvaluationFormController(GeneralEvaluationFormService generalEvaluationFormService){
        this.generalEvaluationFormService = generalEvaluationFormService;
    }




    @RequestMapping(value="/create", method = RequestMethod.POST)
    public GeneralEvaluationFormDto saveGeneralForm(@RequestBody GeneralEvaluationFormDto gefd){

        log.warn("GeneralEval. Controllerına girdi");

        generalEvaluationFormService.save(gefd);
        return  gefd;

    } 

}
