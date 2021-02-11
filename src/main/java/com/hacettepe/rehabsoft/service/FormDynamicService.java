package com.hacettepe.rehabsoft.service;

import com.hacettepe.rehabsoft.dto.AssignedFormDto;


import java.util.List;

public interface FormDynamicService {

    List<AssignedFormDto> getAssignedFormHistory(String tcKimlikNo);

    boolean assignForm(AssignedFormDto assignedFormDto, String tcKimlikNo);
}
