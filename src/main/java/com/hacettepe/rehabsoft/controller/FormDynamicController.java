package com.hacettepe.rehabsoft.controller;

import com.hacettepe.rehabsoft.dto.AssignedFormDto;
import com.hacettepe.rehabsoft.helper.ResponseMessage;
import com.hacettepe.rehabsoft.service.FormDynamicService;
import com.hacettepe.rehabsoft.util.ApiPaths;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping(ApiPaths.FormDynamicPath.CTRL)
@RestController
@Api(value = "/api/form-dynamic")
public class FormDynamicController {

    @Autowired
    ResponseMessage responseMessage;

    @Autowired
    FormDynamicService formDynamicService;

    // For Doctors
    @RequestMapping(value = "/historyy/{patientTcNo}",method = RequestMethod.GET)
    public ResponseEntity<List<AssignedFormDto>> getAllAssignedForm(@PathVariable String patientTcNo){

        List<AssignedFormDto> assignedFormDtoList = formDynamicService.getAssignedFormHistory(patientTcNo);

        return ResponseEntity.ok(assignedFormDtoList);
    }

    @RequestMapping(value="/assignform/{patientTcNo}", method = RequestMethod.POST)
    public ResponseEntity<ResponseMessage> AssignDynamicForm(@RequestBody AssignedFormDto assignedFormDto,@PathVariable String patientTcNo){
        log.warn("Hastaya form atama controller'ı çalışıyor");

        boolean sonuc = formDynamicService.assignForm(assignedFormDto,patientTcNo);

        if(sonuc){
            responseMessage.setResponseMessage("islem basarili");
        }
        else{
            responseMessage.setResponseMessage("Form atama sırasında bir hata meydana geldi");
        }

        return ResponseEntity.ok(responseMessage);
    }

    // For Patients
    @RequestMapping(value = "/requests-not-answered/{patientTcNo}",method = RequestMethod.GET)
    public ResponseEntity<List<AssignedFormDto>> getAllAssignedFormNotAnswered(@PathVariable String patientTcNo){

        List<AssignedFormDto> assignedFormDtoList = formDynamicService.getAssignedFormHistory(patientTcNo);

        return ResponseEntity.ok(assignedFormDtoList);
    }

    @RequestMapping(value = "/request-answered/{patientTcNo}",method = RequestMethod.GET)
    public ResponseEntity<List<AssignedFormDto>> getAllAssignedFormAnswered(@PathVariable String patientTcNo) {

        List<AssignedFormDto> assignedFormDtoList = formDynamicService.getAssignedFormHistory(patientTcNo);

        return ResponseEntity.ok(assignedFormDtoList);
    }




}
