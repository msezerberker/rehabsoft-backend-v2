package com.hacettepe.rehabsoft.service.implementations;

import com.hacettepe.rehabsoft.dto.AssignedFormDto;
import com.hacettepe.rehabsoft.dto.VideoRequestDto;
import com.hacettepe.rehabsoft.entity.AssignedForm;
import com.hacettepe.rehabsoft.entity.VideoRequest;
import com.hacettepe.rehabsoft.helper.SecurityHelper;
import com.hacettepe.rehabsoft.repository.AssignedFormRepository;
import com.hacettepe.rehabsoft.repository.DoctorRepository;
import com.hacettepe.rehabsoft.repository.PatientRepository;
import com.hacettepe.rehabsoft.repository.UserRepository;
import com.hacettepe.rehabsoft.service.FormDynamicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@RequiredArgsConstructor
@Slf4j
@Service(value = "dynamicFormService")
public class FormDynamicServiceImpl implements FormDynamicService {

    @Autowired
    AssignedFormRepository assignedFormRepository;

    private final SecurityHelper securityHelper;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;


    @Override
    public List<AssignedFormDto> getAssignedFormHistory(String tcKimlikNo) {
        List<AssignedForm> assignedFormList = assignedFormRepository.findAllByPatientOrderByCreationDate(patientRepository.getPatientByTcKimlikNo(tcKimlikNo));
        List<AssignedFormDto> assignedFormDtoList =  Arrays.asList(modelMapper.map(assignedFormList, AssignedFormDto[].class));

        if(assignedFormDtoList==null){
            log.warn("Belirtilen hastaya ait form-anket kaydı bulunmuyor");
            return null;}

        return assignedFormDtoList;
    }

    @Override
    public boolean assignForm(AssignedFormDto assignedFormDto, String tcKimlikNo) {
        log.warn("Dynamic Form assign Form metoduna girdi");
        AssignedForm assignedForm = modelMapper.map(assignedFormDto, AssignedForm.class);
        assignedForm.setPatient(patientRepository.getPatientByTcKimlikNo(tcKimlikNo));

        assignedFormRepository.save(assignedForm);

        return true;
        //diger logicleri buraya ekleyecegiz.Dönüs tipini string yapmak daha dogru olur!!!!
    }


}
