package com.hacettepe.rehabsoft.service.implementations;

import com.hacettepe.rehabsoft.dto.AssignedFormDto;
import com.hacettepe.rehabsoft.entity.AssignedForm;
import com.hacettepe.rehabsoft.entity.FormAnswers;
import com.hacettepe.rehabsoft.entity.FormField;
import com.hacettepe.rehabsoft.entity.FormFieldDefaultValue;
import com.hacettepe.rehabsoft.helper.SecurityHelper;
import com.hacettepe.rehabsoft.repository.*;
import com.hacettepe.rehabsoft.service.FormDynamicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service(value = "dynamicFormService")
public class FormDynamicServiceImpl implements FormDynamicService {

    @Autowired
    AssignedFormRepository assignedFormRepository;

    @Autowired
    FormFieldRepository formFieldRepository;

    @Autowired
    FormFieldDefaultValueRepository formFieldDefaultValueRepository;

    @Autowired
    FormAnswersRepository formAnswersRepository;

    private final SecurityHelper securityHelper;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;


    @Override
    @Transactional(readOnly = true)
    public List<AssignedFormDto> getAssignedFormHistory(String tcKimlikNo) {
        List<AssignedForm> assignedFormList = assignedFormRepository.findAllByPatientOrderByCreationDateDesc(patientRepository.getPatientByTcKimlikNo(tcKimlikNo));
        List<AssignedFormDto> assignedFormDtoList =  Arrays.asList(modelMapper.map(assignedFormList, AssignedFormDto[].class));

        if(assignedFormDtoList==null){
            log.warn("Belirtilen hastaya ait form-anket kaydı bulunmuyor");
            return null;}

        return assignedFormDtoList;
    }

    @Override
    @Transactional
    public boolean assignForm(AssignedFormDto assignedFormDto, String tcKimlikNo) {
        log.warn("Dynamic Form assign Form metoduna girdi");

        AssignedForm assignedForm = modelMapper.map(assignedFormDto, AssignedForm.class);
        assignedForm.setPatient(patientRepository.getPatientByTcKimlikNo(tcKimlikNo));

        assignedFormRepository.save(assignedForm);

        for(FormField field : assignedForm.getFormDynamic().getFormFieldCollection()){
            field.setFormDynamic(assignedForm.getFormDynamic());
            formFieldRepository.save(field);
            if(field.getFieldType().equals("SECMELI") || field.getFieldType().equals("COKLU_SECMELI") ){
                for(FormFieldDefaultValue defaultValue : field.getFormFieldDefaultValueCollection()){
                    defaultValue.setFormField(field);
                    formFieldDefaultValueRepository.save(defaultValue);
                }
            }
        }

        return true;
        //diger logicleri buraya ekleyecegiz.Dönüs tipini string yapmak daha dogru olur!!!!
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssignedFormDto> getAssignedFormNotAnswered(String tcKimlikNo) {
        List<AssignedForm> assignedFormList = assignedFormRepository.findAllByPatientAndIsAnsweredOrderByCreationDateDesc(patientRepository.getPatientByTcKimlikNo(tcKimlikNo),false);
        List<AssignedFormDto> assignedFormDtoList =  Arrays.asList(modelMapper.map(assignedFormList, AssignedFormDto[].class));

        if(assignedFormDtoList==null){
            log.warn("Cevap bekleyen form-anket talebiniz bulunmuyor");
            return null;
        }

        return assignedFormDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssignedFormDto> getAssignedFormAnswered(String tcKimlikNo) {
        List<AssignedForm> assignedFormList = assignedFormRepository.findAllByPatientAndIsAnsweredOrderByCreationDateDesc(patientRepository.getPatientByTcKimlikNo(tcKimlikNo),true);
        List<AssignedFormDto> assignedFormDtoList =  Arrays.asList(modelMapper.map(assignedFormList, AssignedFormDto[].class));

        if(assignedFormDtoList==null){
            log.warn("Cevapladığınız form-anket talebiniz bulunmuyor");
            return null;
        }

        return assignedFormDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public AssignedFormDto getAssignedFormById(int id) {
        long lid = id;
        AssignedForm assignedForm = assignedFormRepository.findById(lid).get();
        AssignedFormDto assignedFormDto = modelMapper.map(assignedForm,AssignedFormDto.class);

        if (assignedFormDto == null){
            log.warn("Forma erişilemedi !!!");
            return null;
        }
        return assignedFormDto;
    }

    @Override
    @Transactional
    public boolean answerTheForm(AssignedFormDto assignedFormDto, String formID) {

        long lFormID = Integer.parseInt(formID);
        AssignedForm assignedFormWanswers = modelMapper.map(assignedFormDto, AssignedForm.class);
        AssignedForm assignedForm = assignedFormRepository.findById(lFormID).get();
        assignedForm.setFormAnswersCollection(assignedFormWanswers.getFormAnswersCollection());
        for(FormAnswers answer : assignedFormWanswers.getFormAnswersCollection()){
            answer.setAssignedForm(assignedForm);
            answer.setFormField(formFieldRepository.findById(answer.getFormField().getId()).get());
            formAnswersRepository.save(answer);
        }
        assignedForm.setAnswered(true);
        assignedFormRepository.save(assignedForm);

        return true;
    }
}
