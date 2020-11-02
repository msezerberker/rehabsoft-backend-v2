package com.hacettepe.rehabsoft.controller;

import com.hacettepe.rehabsoft.util.ApiPaths;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping(ApiPaths.GeneralEvaluationFormPath.CTRL)
@RestController
@Api(value = "/api/patient/generalevaluationform")
public class GeneralEvaluationFormController {
}
