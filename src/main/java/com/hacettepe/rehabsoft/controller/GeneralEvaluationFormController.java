package com.hacettepe.rehabsoft.controller;

import com.hacettepe.rehabsoft.dto.GeneralEvaluationFormDto;
import com.hacettepe.rehabsoft.helper.ResponseMessage;
import com.hacettepe.rehabsoft.service.GeneralEvaluationFormService;
import com.hacettepe.rehabsoft.util.ApiPaths;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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


    private final ResponseMessage responseMessage;
    private final GeneralEvaluationFormService generalEvaluationFormService;

    public GeneralEvaluationFormController(GeneralEvaluationFormService generalEvaluationFormService,ResponseMessage responseMessage){
        this.generalEvaluationFormService = generalEvaluationFormService;
        this.responseMessage = responseMessage;
    }




    @RequestMapping(value="/create", method = RequestMethod.POST)
    public ResponseEntity<ResponseMessage> saveGeneralForm(@RequestParam(value = "botoxImageFile", required = false) MultipartFile botoxImage,
                                                           @RequestParam(value = "appliedSurgeryEpicrisisImages", required = false) MultipartFile[] epicrisisImages,
                                                           @RequestParam(value = "otherOrthesisImages", required = false) MultipartFile[] otherOrthesisImages,
                                                           @RequestParam("model") String gefd
    ){
        log.warn("GeneralEval. Controllerına girdi");

        if(generalEvaluationFormService.isGeneralEvaluationFormExist()){
            responseMessage.setResponseMessage("Daha önce zaten bir form doldurdunuz");
            return ResponseEntity.badRequest().body(responseMessage);

        }


        Boolean success = generalEvaluationFormService.save(gefd, botoxImage, epicrisisImages, otherOrthesisImages);
        if(!success){
            responseMessage.setResponseMessage("Formunuzun kaydı sırasında beklenmedik bir hata meydana geldi.Lütfen tekrar deneyin");
            return ResponseEntity.badRequest().body(responseMessage);
        }

        responseMessage.setResponseMessage("Formunuz başarı ile kaydedildi!");
        return  ResponseEntity.ok(responseMessage);

    } 

}
