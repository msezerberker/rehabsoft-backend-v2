package com.hacettepe.rehabsoft.service.implementations;


import com.hacettepe.rehabsoft.dto.PatientDetailsDto;
import com.hacettepe.rehabsoft.dto.PatientDto;
import com.hacettepe.rehabsoft.dto.UserDto;
import com.hacettepe.rehabsoft.entity.Parent;
import com.hacettepe.rehabsoft.entity.Patient;
import com.hacettepe.rehabsoft.entity.User;
import com.hacettepe.rehabsoft.helper.SecurityHelper;
import com.hacettepe.rehabsoft.repository.PatientRepository;
import com.hacettepe.rehabsoft.repository.UserRepository;
import com.hacettepe.rehabsoft.service.ParentService;
import com.hacettepe.rehabsoft.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class PatientServiceImpl implements PatientService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SecurityHelper securityHelper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParentService parentService;

    @Autowired
    private PatientRepository patientRepository;




    public Boolean isPatientAlreadySaved(){
        String username = securityHelper.getUsername();
        Patient patient = patientRepository.getPatientByUser(userRepository.findByUsername(username));
        if(patient!=null){
            return true;
        }
        return false;
    }


    public Boolean isIdentityNoExists(String tcKimlikNo){
        Patient patient = patientRepository.getPatientByTcKimlikNo(tcKimlikNo);
        if(patient!=null){
            return true;
        }
        return false;
    }



    @Override
    public PatientDto savePatient(PatientDto patientDto){

        log.warn("Patient servisine girdi:Save:");
        Patient patient = modelMapper.map(patientDto, Patient.class);
        //Log-in olmus user'ın ismi Spring Security'den alınarak atama yapılır
        patient.setUser(userRepository.findByUsername(securityHelper.getUsername()));
        patient.setGeneralEvaluationForm(null);

        //Simdilik doktor ataması yapmıyoruz.Database tarafında otomatik olarak 1 veriyor
        //Doktor servisini yazınca orada bir setDoc oluşturup burayı tekrar yazacağız
        patient.setDoctor(null);


        for(Parent parent:patient.getParentCollection()){
            parentService.saveParent(parent);
        }

        patientRepository.save(patient);

        return patientDto;
    }

    public List<PatientDetailsDto> getAllPatientUsers(){


        List<Patient> patientList = patientRepository.findAll();

        /*gefd doldurulmamıssa sil
        for(Patient patient:patientList){
            if(patient.getGeneralEvaluationForm()==null){
                patientList.remove(patient);
            }
        }

         */
        List<PatientDetailsDto> patientDetailsDtoList=  Arrays.asList(modelMapper.map(patientList, PatientDetailsDto[].class));

        return patientDetailsDtoList;
    }


}
