package com.hacettepe.rehabsoft.controller;

import com.hacettepe.rehabsoft.dto.GeneralEvaluationFormDto;
import com.hacettepe.rehabsoft.dto.UserDto;
import com.hacettepe.rehabsoft.helper.SecurityHelper;
import com.hacettepe.rehabsoft.service.GeneralEvaluationFormService;
import com.hacettepe.rehabsoft.util.ApiPaths;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@Slf4j
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping(ApiPaths.GeneralEvaluationFormPath.CTRL)
@RestController
@Api(value = "/api/patient/generalevaluationform")
public class GeneralEvaluationFormController {

    @Autowired
    GeneralEvaluationFormService generalEvaluationFormService;



    @RequestMapping(value="/create", method = RequestMethod.POST)
    public GeneralEvaluationFormDto saveGeneralForm(@RequestBody GeneralEvaluationFormDto gefd){

        log.warn("GeneralEval. Controllerına girdi");

        generalEvaluationFormService.save(gefd);
        return  gefd;

    } 

}
