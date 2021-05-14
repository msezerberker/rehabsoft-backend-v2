package com.hacettepe.rehabsoft.controller;


import com.hacettepe.rehabsoft.dto.PhysiotherapyProgramDto;
import com.hacettepe.rehabsoft.helper.ResponseMessage;
import com.hacettepe.rehabsoft.service.ExerciseProgramAssignService;
import com.hacettepe.rehabsoft.util.ApiPaths;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@CrossOrigin(origins = ApiPaths.LOCAL_CLIENT_BASE_PATH, maxAge = 3600)
@RequestMapping(ApiPaths.ExerciseProgramAssignPath.CTRL)
@RestController
@Api(value = "/api/assign-exercise-program")
public class ExerciseProgramAssignController {

    @Autowired
    ResponseMessage responseMessage;

    @Autowired
    ExerciseProgramAssignService exerciseProgramAssignService;

    @RequestMapping(value = "/get-all-assigned/{tcKimlikNo}",method = RequestMethod.GET)
    public ResponseEntity<List<PhysiotherapyProgramDto>> getAllAssignedForm(@PathVariable String tcKimlikNo){

        List<PhysiotherapyProgramDto> physiotherapyProgramDtoList = exerciseProgramAssignService.getAllAssignedExercisePrograms(tcKimlikNo);

        return ResponseEntity.ok(physiotherapyProgramDtoList);
    }

    @RequestMapping(value="/assign-new", method = RequestMethod.POST)
    public ResponseEntity<ResponseMessage> AssignDynamicForm(@RequestBody PhysiotherapyProgramDto physiotherapyProgramDto){
        log.warn("Hastaya programatama controller'ı çalışıyor");

        boolean sonuc = exerciseProgramAssignService.assignProgram(physiotherapyProgramDto);

        if(sonuc){
            responseMessage.setResponseMessage("islem basarili");
        }
        else{
            responseMessage.setResponseMessage("Program atama sırasında bir hata meydana geldi");
        }

        return ResponseEntity.ok(responseMessage);
    }
}
