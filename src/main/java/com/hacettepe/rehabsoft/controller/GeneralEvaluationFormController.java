package com.hacettepe.rehabsoft.controller;

import com.hacettepe.rehabsoft.dto.GeneralEvaluationFormDto;
import com.hacettepe.rehabsoft.service.GeneralEvaluationFormService;
import com.hacettepe.rehabsoft.util.ApiPaths;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<String> saveGeneralForm( @RequestParam(value = "botoxImageFile", required = false) MultipartFile botoxImage,
                                                   @RequestParam(value = "appliedSurgeryEpicrisisImages", required = false) MultipartFile[] epicrisisImages,
                                                   @RequestParam(value = "otherOrthesisImages", required = false) MultipartFile[] otherOrthesisImages,
                                                   @RequestParam("model") String gefd
    ){
        log.warn("GeneralEval. Controllerına girdi");

        if(generalEvaluationFormService.isGeneralEvaluationFormExist())
            return ResponseEntity.badRequest().body("Daha önce zaten bir form doldurdunuz");


        Boolean success = generalEvaluationFormService.save(gefd, botoxImage, epicrisisImages);
        if(!success){
            return ResponseEntity.badRequest().body("Formunuzun kaydı sırasında beklenmedik bir hata meydana geldi.Lütfen tekrar deneyin");
        }

        return  ResponseEntity.ok("Formunuz başarı ile kaydedildi!");

    } 

}
