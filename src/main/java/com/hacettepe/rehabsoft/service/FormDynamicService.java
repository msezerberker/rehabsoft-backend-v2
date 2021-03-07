package com.hacettepe.rehabsoft.service;

import com.hacettepe.rehabsoft.dto.AssignedFormDto;


import java.util.List;

public interface FormDynamicService {

    List<AssignedFormDto> getAssignedFormHistory(String tcKimlikNo);

    boolean assignForm(AssignedFormDto assignedFormDto, String tcKimlikNo);

    List<AssignedFormDto> getAssignedFormNotAnswered(String tcKimlikNo);

    List<AssignedFormDto> getAssignedFormAnswered(String tcKimlikNo);

    AssignedFormDto getAssignedFormById(int id);

    boolean answerTheForm(AssignedFormDto assignedFormDto, String formID);


}
