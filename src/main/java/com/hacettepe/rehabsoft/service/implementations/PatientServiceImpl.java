package com.hacettepe.rehabsoft.service.implementations;


import com.hacettepe.rehabsoft.dto.PatientDto;
import com.hacettepe.rehabsoft.entity.AppliedSurgery;
import com.hacettepe.rehabsoft.entity.GeneralEvaluationForm;
import com.hacettepe.rehabsoft.entity.Parent;
import com.hacettepe.rehabsoft.entity.Patient;
import com.hacettepe.rehabsoft.helper.SecurityHelper;
import com.hacettepe.rehabsoft.repository.GeneralEvaluationFormRepository;
import com.hacettepe.rehabsoft.repository.PatientRepository;
import com.hacettepe.rehabsoft.repository.UserRepository;
import com.hacettepe.rehabsoft.service.ParentService;
import com.hacettepe.rehabsoft.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PatientServiceImpl implements PatientService {

    private final ModelMapper modelMapper;
    private final SecurityHelper securityHelper;
    private final UserRepository userRepository;
    private final ParentService parentService;
    private final PatientRepository patientRepository;

    public PatientServiceImpl(ModelMapper modelMapper, SecurityHelper securityHelper, UserRepository userRepository, ParentService parentService, PatientRepository patientRepository) {
        this.modelMapper = modelMapper;
        this.securityHelper = securityHelper;
        this.userRepository = userRepository;
        this.parentService = parentService;
        this.patientRepository = patientRepository;
    }

    @Override
    public PatientDto savePatient(PatientDto patientDto){

        log.warn("Patient servisine girdi:Save:");
        Patient patient = modelMapper.map(patientDto, Patient.class);

        //Log-in olmus user'ın ismi Spring Security'den alınarak atama yapılır
        patient.setUser(userRepository.findByUsername(securityHelper.getUsername()));

        patient.setGeneralEvaluationForm(null);

        for(Parent parent:patient.getParentCollection()){
            parentService.saveParent(parent);
        }

        patientRepository.save(patient);

        return patientDto;
    }

    @Override
    public boolean isAlreadySaved(String tcKimlik) {
        Patient alreadySaved = patientRepository.getPatientByTcKimlikNo(tcKimlik);
        if(alreadySaved != null){//already saved in DB
            return true;
        }
        else
            return false;
    }

    @Override
    public boolean isPatientSaved() {

        Patient alreadySaved = patientRepository.getPatientByUser(userRepository.findByUsername(securityHelper.getUsername()));
        if(alreadySaved != null){//already saved in DB
            return true;
        }
        else
            return false;
    }

}
