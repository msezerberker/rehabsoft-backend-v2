package com.hacettepe.rehabsoft.service;

import com.hacettepe.rehabsoft.dto.ExerciseDto;
import java.util.List;

public interface ExerciseSevice {

    List<ExerciseDto> getAll();

    ExerciseDto getById(Long id);

    ExerciseDto save(ExerciseDto exerciseDto);

}
