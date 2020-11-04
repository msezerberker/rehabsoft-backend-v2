package com.hacettepe.rehabsoft.service.implementations;

import com.hacettepe.rehabsoft.dto.ExerciseDto;
import com.hacettepe.rehabsoft.entity.Exercise;
import com.hacettepe.rehabsoft.repository.ExerciseRepository;
import com.hacettepe.rehabsoft.service.ExerciseSevice;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service(value = "exerciseService")
public class ExerciseServiceImpl implements ExerciseSevice {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ExerciseRepository exerciseRepository;


    @Override
    public List<ExerciseDto> getAll(){
        List<Exercise> exercises = exerciseRepository.findAll();
        return  Arrays.asList(modelMapper.map(exercises, ExerciseDto[].class));
    }

    @Override
    public ExerciseDto getById(Long id){
        Exercise exercise = exerciseRepository.getOne(id);
        return modelMapper.map(exercise, ExerciseDto.class);
    }

    @Override
    @Transactional
    public ExerciseDto save(ExerciseDto exerciseDto) {
        Exercise tempExercise = modelMapper.map(exerciseDto, Exercise.class);
        tempExercise = exerciseRepository.save(tempExercise);
        exerciseDto.setId(tempExercise.getId());
        return exerciseDto;
    }

}
