package com.hacettepe.rehabsoft.service.implementations;

import com.hacettepe.rehabsoft.dto.ExerciseDto;
import com.hacettepe.rehabsoft.dto.UserDto;
import com.hacettepe.rehabsoft.entity.Exercise;
import com.hacettepe.rehabsoft.entity.User;
import com.hacettepe.rehabsoft.helper.SecurityHelper;
import com.hacettepe.rehabsoft.repository.ExerciseRepository;
import com.hacettepe.rehabsoft.repository.UserRepository;
import com.hacettepe.rehabsoft.service.ExerciseService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service//(value = "exerciseService")
public class ExerciseServiceImpl implements ExerciseService {

    private final ModelMapper modelMapper;
    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;
    private final SecurityHelper securityHelper;

    public ExerciseServiceImpl(ModelMapper modelMapper, ExerciseRepository exerciseRepository,
    UserRepository userRepository,SecurityHelper securityHelper) {
        this.modelMapper = modelMapper;
        this.exerciseRepository = exerciseRepository;
        this.userRepository = userRepository;
        this.securityHelper = securityHelper;
    }

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
    public String save(ExerciseDto exerciseDto) {

        if(this.isExerciseNameExists(exerciseDto.getExerciseName())){
                return "Bu isim başka bir egzersiz için kullanılıyor.Lütfen başka bir isim seçiniz.";
            }
        Exercise tempExercise = modelMapper.map(exerciseDto, Exercise.class);
        tempExercise.setUser(userRepository.findByUsername(securityHelper.getUsername()));
        tempExercise = exerciseRepository.save(tempExercise);
        exerciseDto.setId(tempExercise.getId());

        return "Egzersiz başarıyla kaydedildi!";
    }


    @Override
    @Transactional
    public Boolean delete(Long id) {
        try{
            exerciseRepository.deleteById(id);
            log.warn("Egzersiz silindi=>"+ id);
            return Boolean.TRUE;
        }
        catch (Exception e) {
            log.error("Deletion of Exercise is Failed=>", e);
            return Boolean.FALSE;
        }
    }


    @Override
    public Boolean isExerciseNameExists(String name) {
        if(exerciseRepository.getByExerciseName(name) ==null){
            return false;
        }
        return true;
    }

    @Override
    public String updateExercise(ExerciseDto exerciseDto){
        log.warn("exercise update is on");
        Exercise dbExercise = exerciseRepository.getOne(exerciseDto.getId());

        if(!dbExercise.getExerciseName().equals(exerciseDto.getExerciseName()) ){
            if(this.isExerciseNameExists(exerciseDto.getExerciseName())){
                return "Bu isim başka bir egzersiz için kullanılıyor.Lütfen başka bir isim seçiniz.";
            }
        }

        dbExercise.setExerciseName(exerciseDto.getExerciseName());
        dbExercise.setExerciseContent(exerciseDto.getExerciseContent());

        exerciseRepository.save(dbExercise);
        return "Egzersiz ile ilgili değişiklikler başarıyla kaydedildi!";

    }


}
