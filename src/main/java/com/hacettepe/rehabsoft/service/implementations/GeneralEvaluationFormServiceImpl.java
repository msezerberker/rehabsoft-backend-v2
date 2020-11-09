package com.hacettepe.rehabsoft.service.implementations;

import com.hacettepe.rehabsoft.dto.GeneralEvaluationFormDto;
import com.hacettepe.rehabsoft.entity.*;
import com.hacettepe.rehabsoft.helper.SecurityHelper;
import com.hacettepe.rehabsoft.repository.*;
import com.hacettepe.rehabsoft.service.GeneralEvaluationFormService;
import com.hacettepe.rehabsoft.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;


@Service
@Slf4j
public class GeneralEvaluationFormServiceImpl implements GeneralEvaluationFormService {

    private void fillExpectationsAboutProgram(Collection<ExpectationsAboutProgram> exps,GeneralEvaluationForm tempForm){
        for(ExpectationsAboutProgram e:exps){
            e.setGeneralEvaluationForm(tempForm);
        }
    }

    private void fillOrthesisInfoCollection(Collection<OrthesisInfo> exps,GeneralEvaluationForm tempForm){
        for(OrthesisInfo e:exps){
            e.setGeneralEvaluationForm(tempForm);
        }
    }

    private void fillOtherOrthesisInfo(Collection<OtherOrthesisInfo> exps,GeneralEvaluationForm tempForm){
        for(OtherOrthesisInfo e:exps){
            e.setGeneralEvaluationForm(tempForm);
        }
    }

    private void fillUsedMedicine(Collection<UsedMedicine> exps,GeneralEvaluationForm tempForm){
        for(UsedMedicine e:exps){
            e.setGeneralEvaluationForm(tempForm);
        }
    }


    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private GeneralEvaluationFormRepository generalEvaluationFormRepository;

    @Autowired
    private AppliedSurgeryRepository appliedSurgeryRepository;

    @Autowired
    private CoexistingDiseasesRepository coexistingDiseasesRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SecurityHelper securityHelper;

    @Autowired
    PatientService patientService;


    @Override
    public GeneralEvaluationFormDto save(GeneralEvaluationFormDto gefd){
        log.warn("GeneralEval. servisine girdi:Tarih:" + gefd.getLastBotoxDate());
        GeneralEvaluationForm tempForm = modelMapper.map(gefd, GeneralEvaluationForm.class);


        tempForm.getDiseaseOfMotherPregnancy().setGeneralEvaluationForm(tempForm);
        tempForm.getHyperbilirubinemia().setGeneralEvaluationForm(tempForm);
        tempForm.getAfterBirthReasonCerebralPalsy().setGeneralEvaluationForm(tempForm);
        tempForm.getBotoxTreatment().setGeneralEvaluationForm(tempForm);
        tempForm.getVisualImpairment().setGeneralEvaluationForm(tempForm);
        tempForm.getHearingImpairment().setGeneralEvaluationForm(tempForm);


        fillExpectationsAboutProgram(tempForm.getExpectationsAboutProgramCollection(),tempForm);
        fillOrthesisInfoCollection(tempForm.getOrthesisInfoCollection(),tempForm);
        fillOtherOrthesisInfo(tempForm.getOtherOrthesisInfoCollection(),tempForm);
        fillUsedMedicine(tempForm.getUsedMedicineCollection(),tempForm);

        for(AppliedSurgery a:tempForm.getAppliedSurgeryCollection()){
            appliedSurgeryRepository.save(a);
        }

        for(CoexistingDiseases a:tempForm.getCoexistingDiseasesCollection()){
            coexistingDiseasesRepository.save(a);
        }

            /*
        log.warn("before-save-patient");
        tempForm.setPatient(null);
        GeneralEvaluationForm generalEvaluationForm = generalEvaluationFormRepository.save(tempForm);
        log.warn("after-save-patient. GEF id:" + generalEvaluationForm.getId());

*/

        //Patient'i general evaluation form'a baglıyor
        //Once username kullanarak mevcut user objesi getiriliyor
        //bu user objesi kullanılarak patient objesi getiriliyor


        //Calisan kısım
        tempForm.setPatient(patientRepository.getPatientByUser(userRepository.findByUsername(securityHelper.getUsername())));

        log.warn("Son kayit islemi : ");

        generalEvaluationFormRepository.save(tempForm);

/*
        GeneralEvaluationForm savedForm = generalEvaluationFormRepository.save(tempForm);
        Patient patient = patientRepository.getPatientByUser(userRepository.findByUsername(securityHelper.getUsername()));
        patient.setGeneralEvaluationForm(savedForm);
        patientRepository.save(patient);

*/
        return gefd;
    }

    @Override
    public boolean isGeneralEvaluationFormExist() {

        Patient patientByUser = patientRepository.getPatientByUser(userRepository.findByUsername(securityHelper.getUsername()));
        if(patientByUser.getGeneralEvaluationForm() != null){//already saved in DB
            return true;
        }
        else
            return false;
    }


}
