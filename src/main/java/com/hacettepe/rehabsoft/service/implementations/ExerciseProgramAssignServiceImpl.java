package com.hacettepe.rehabsoft.service.implementations;

import com.hacettepe.rehabsoft.dto.PhysiotherapyProgramDto;
import com.hacettepe.rehabsoft.entity.AddedExerciseInProgram;
import com.hacettepe.rehabsoft.entity.PhysiotherapyProgram;
import com.hacettepe.rehabsoft.entity.ScheduledExercise;
import com.hacettepe.rehabsoft.repository.*;
import com.hacettepe.rehabsoft.service.ExerciseProgramAssignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service(value = "exerciseProgramAssignService")
public class ExerciseProgramAssignServiceImpl implements ExerciseProgramAssignService {

    @Autowired
    AddedExerciseInProgramRepository addedExerciseInProgramRepository;

    @Autowired
    PhysiotherapyProgramRepository physiotherapyProgramRepository;

    @Autowired
    ScheduledExerciseRepository scheduledExerciseRepository;

    private final ModelMapper modelMapper;
    private final PatientRepository patientRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PhysiotherapyProgramDto> getAllAssignedExercisePrograms(String tcKimlikNo) {
        List<PhysiotherapyProgram> physiotherapyPrograms = physiotherapyProgramRepository.findAllByPatientOrderByCreationDateDesc(patientRepository.getPatientByTcKimlikNo(tcKimlikNo));

        if( physiotherapyPrograms == null || physiotherapyPrograms.isEmpty()){
            log.warn("Belirtilen hastaya ait fegzersiz programı kaydı bulunmuyor");
            return Collections.emptyList();
        }

        List<PhysiotherapyProgramDto> physiotherapyProgramDtoList =  Arrays.asList(modelMapper.map(physiotherapyPrograms, PhysiotherapyProgramDto[].class));

        if( physiotherapyProgramDtoList == null || physiotherapyProgramDtoList.isEmpty()){
            log.warn("Belirtilen hastaya ait fegzersiz programı kaydı bulunmuyor");
            return Collections.emptyList();
        }

        return physiotherapyProgramDtoList;
    }

    @Override
    @Transactional
    public boolean assignProgram(PhysiotherapyProgramDto physiotherapyProgramDto) {

        PhysiotherapyProgram physiotherapyProgram = modelMapper.map(physiotherapyProgramDto, PhysiotherapyProgram.class);
        List<AddedExerciseInProgram>  addedExerciseInProgramList =  (List) physiotherapyProgram.getExerciseInProgramsList();
        physiotherapyProgramRepository.save(physiotherapyProgram);

        for (AddedExerciseInProgram addedExerciseInProgram : addedExerciseInProgramList){

            List<ScheduledExercise> scheduledExerciseList = (List) addedExerciseInProgram.getScheduledExerciseCollection();
            addedExerciseInProgram.setPhysiotherapyProgram(physiotherapyProgramRepository.findById(physiotherapyProgram.getId()).get());
            addedExerciseInProgramRepository.save(addedExerciseInProgram);

            for (ScheduledExercise scheduledExercise : scheduledExerciseList){
                scheduledExercise.setAddedExerciseInProgram(addedExerciseInProgramRepository.findById(addedExerciseInProgram.getId()).get());
                scheduledExercise.setApplied(false);
                scheduledExerciseRepository.save(scheduledExercise);
            }

        }

        return true;
    }
}
