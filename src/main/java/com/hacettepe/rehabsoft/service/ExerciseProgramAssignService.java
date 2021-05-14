package com.hacettepe.rehabsoft.service;

import com.hacettepe.rehabsoft.dto.PhysiotherapyProgramDto;
import java.util.List;

public interface ExerciseProgramAssignService {

    List<PhysiotherapyProgramDto> getAllAssignedExercisePrograms(String tcKimlikNo);
    boolean assignProgram(PhysiotherapyProgramDto physiotherapyProgramDto);
}
