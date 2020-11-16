package com.hacettepe.rehabsoft.service.implementations;

import com.hacettepe.rehabsoft.dto.GeneralEvaluationFormDto;
import com.hacettepe.rehabsoft.entity.*;
import com.hacettepe.rehabsoft.helper.SecurityHelper;
import com.hacettepe.rehabsoft.repository.*;
import com.hacettepe.rehabsoft.service.GeneralEvaluationFormService;
import com.hacettepe.rehabsoft.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;


@Service
@Slf4j
public class GeneralEvaluationFormServiceImpl implements GeneralEvaluationFormService {

    private final ModelMapper modelMapper;
    private final GeneralEvaluationFormRepository generalEvaluationFormRepository;
    private final AppliedSurgeryRepository appliedSurgeryRepository;
    private final CoexistingDiseasesRepository coexistingDiseasesRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final SecurityHelper securityHelper;

    public GeneralEvaluationFormServiceImpl(ModelMapper modelMapper, GeneralEvaluationFormRepository generalEvaluationFormRepository, AppliedSurgeryRepository appliedSurgeryRepository, CoexistingDiseasesRepository coexistingDiseasesRepository, PatientRepository patientRepository, UserRepository userRepository, SecurityHelper securityHelper, PatientService patientService) {
        this.modelMapper = modelMapper;
        this.generalEvaluationFormRepository = generalEvaluationFormRepository;
        this.appliedSurgeryRepository = appliedSurgeryRepository;
        this.coexistingDiseasesRepository = coexistingDiseasesRepository;
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
        this.securityHelper = securityHelper;
    }



    private void fillExpectationsAboutProgram(Collection<ExpectationsAboutProgram> exps, GeneralEvaluationForm tempForm){
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



    @Override
    public GeneralEvaluationFormDto save(GeneralEvaluationFormDto gefd){
        log.warn("GeneralEval. servisine girdi:Tarih:" );
        GeneralEvaluationForm tempForm = modelMapper.map(gefd, GeneralEvaluationForm.class);

        log.warn("GeneralEval: Mapleme başarılı" );

 /*
        tempForm.getDiseaseOfMotherPregnancy().setGeneralEvaluationForm(tempForm);

        tempForm.getHyperbilirubinemia().setGeneralEvaluationForm(tempForm);
        tempForm.getAfterBirthReasonCerebralPalsy().setGeneralEvaluationForm(tempForm);
        tempForm.getBotoxTreatment().setGeneralEvaluationForm(tempForm);
        tempForm.getVisualImpairment().setGeneralEvaluationForm(tempForm);
        tempForm.getHearingImpairment().setGeneralEvaluationForm(tempForm);
        tempForm.getEpilepsy().setGeneralEvaluationForm(tempForm);
        */
        log.warn("Gen. Ev. Form- One-To-one Bitti. servisine girdi" );

        fillExpectationsAboutProgram(tempForm.getExpectationsAboutProgramCollection(),tempForm);
        fillOrthesisInfoCollection(tempForm.getOrthesisInfoCollection(),tempForm);
        fillOtherOrthesisInfo(tempForm.getOtherOrthesisInfoCollection(),tempForm);
        fillUsedMedicine(tempForm.getUsedMedicineCollection(),tempForm);

        log.warn("Gen. Ev. Form- Many-to-One Bitti. servisine girdi" );

        for(AppliedSurgery a:tempForm.getAppliedSurgeryCollection()){
            appliedSurgeryRepository.save(a);
        }

        for(CoexistingDiseases a:tempForm.getCoexistingDiseasesCollection()){
            coexistingDiseasesRepository.save(a);
        }

        log.warn("Gen. Ev. Form- Many-to-Many Bitti. servisine girdi" );

        //Calisan kısım
        tempForm.setPatient(patientRepository.getPatientByUser(userRepository.findByUsername(securityHelper.getUsername())));
        log.warn("Son kayit islemi : ");


        generalEvaluationFormRepository.save(tempForm);

        return gefd;
    }



    @Override
    public boolean isGeneralEvaluationFormExist() {

        Patient patientByUser = patientRepository.getPatientByUser(userRepository.findByUsername(securityHelper.getUsername()));
        if(patientByUser != null){
            if(patientByUser.getGeneralEvaluationForm() != null ){//already saved in DB
                return true;
            }
        }

        return false;
    }


}
