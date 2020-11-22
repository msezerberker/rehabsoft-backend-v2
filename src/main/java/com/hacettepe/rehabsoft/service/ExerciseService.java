package com.hacettepe.rehabsoft.service;

import com.hacettepe.rehabsoft.dto.ExerciseDto;
import java.util.List;

public interface ExerciseService {

    List<ExerciseDto> getAll();

    ExerciseDto getById(Long id);

    String save(ExerciseDto exerciseDto);

    Boolean delete(Long id);

    String updateExercise(ExerciseDto exerciseDto);

    Boolean isExerciseNameExists(String name);

}
