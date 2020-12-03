package com.hacettepe.rehabsoft.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hacettepe.rehabsoft.dto.ExerciseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ExerciseService {

    List<ExerciseDto> getAll();

    ExerciseDto getById(Long id);

    String save(String exerciseJSON, MultipartFile[] exerciseMedia ) throws Exception;

    Boolean delete(Long id);

    String updateExercise(ExerciseDto exerciseDto);

    Boolean isExerciseNameExists(String name);

}
