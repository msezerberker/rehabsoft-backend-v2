package com.hacettepe.rehabsoft.controller;

import com.hacettepe.rehabsoft.dto.ExerciseDto;
import com.hacettepe.rehabsoft.helper.ResponseMessage;
import com.hacettepe.rehabsoft.service.ExerciseService;
import com.hacettepe.rehabsoft.util.ApiPaths;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping(ApiPaths.ExercisePath.CTRL)
@RestController
@Api(value = "/api/exercises")
public class ExerciseController {


    @Autowired
    ResponseMessage responseMessage;

    @Autowired
    ExerciseService exerciseService;

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ResponseEntity<ResponseMessage> createExercise(@Valid @RequestBody ExerciseDto exerciseDto){
        log.warn("exercise creation controllerına girdi");
        String message = exerciseService.save(exerciseDto);
        responseMessage.setResponseMessage(message);
        return ResponseEntity.ok(responseMessage);
    }

    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteExercise(@PathVariable(value = "id") Long id){
        log.warn("exercise delete controllerına girdi: "+id);
        exerciseService.delete(id);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public ResponseEntity<List<ExerciseDto>> listExercises(){
        log.warn("listExercises metoduna girdi");
        List<ExerciseDto> exerciseList = exerciseService.getAll();
        return ResponseEntity.ok(exerciseList);
    }


    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public ResponseEntity<ResponseMessage> updateExercise(@Valid @RequestBody ExerciseDto exerciseDto){
        log.warn("exercise update controllerına girdi");
        String message = exerciseService.updateExercise(exerciseDto);
        responseMessage.setResponseMessage(message);
        return ResponseEntity.ok(responseMessage);
    }

}
