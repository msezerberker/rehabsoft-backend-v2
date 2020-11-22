package com.hacettepe.rehabsoft.service.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hacettepe.rehabsoft.dto.GeneralEvaluationFormDto;
import com.hacettepe.rehabsoft.entity.*;
import com.hacettepe.rehabsoft.helper.SecurityHelper;
import com.hacettepe.rehabsoft.repository.*;
import com.hacettepe.rehabsoft.service.GeneralEvaluationFormService;
import com.hacettepe.rehabsoft.util.ApiPaths;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;


@Service
@Slf4j
@RequiredArgsConstructor
public class GeneralEvaluationFormServiceImpl implements GeneralEvaluationFormService {



    private final ModelMapper modelMapper;
    private final GeneralEvaluationFormRepository generalEvaluationFormRepository;
    private final AppliedSurgeryRepository appliedSurgeryRepository;
    private final CoexistingDiseasesRepository coexistingDiseasesRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final SecurityHelper securityHelper;
    private final BotoxTreatmentRepository botoxTreatmentRepository;
    private final ObjectMapper objectMapper;


    private void fillExpectationsAboutProgram(Collection<ExpectationsAboutProgram> exps, GeneralEvaluationForm tempForm){
        if(exps!=null){
            for(ExpectationsAboutProgram e:exps){
                e.setGeneralEvaluationForm(tempForm);
            }
        }
    }

    private void fillOrthesisInfoCollection(Collection<OrthesisInfo> exps,GeneralEvaluationForm tempForm){
        if(exps !=null){
            for(OrthesisInfo e:exps){
                e.setGeneralEvaluationForm(tempForm);
            }
        }
    }

    private void fillOtherOrthesisInfo(Collection<OtherOrthesisInfo> exps,GeneralEvaluationForm tempForm){
        if(exps!=null){
            for(OtherOrthesisInfo e:exps){
                e.setGeneralEvaluationForm(tempForm);
            }
        }
    }

    private void fillUsedMedicine(Collection<UsedMedicine> exps,GeneralEvaluationForm tempForm){
        if(exps!=null){
        for(UsedMedicine e:exps){
            e.setGeneralEvaluationForm(tempForm);
        }}
    }


    private void setBidirectionalOneToOne(GeneralEvaluationForm tempForm, MultipartFile botoxImage){
        if(tempForm.getDiseaseOfMotherPregnancy() !=null){
            tempForm.getDiseaseOfMotherPregnancy().setGeneralEvaluationForm(tempForm);
        }

        if(tempForm.getHyperbilirubinemia() != null){
            tempForm.getHyperbilirubinemia().setGeneralEvaluationForm(tempForm);
        }

        if(tempForm.getAfterBirthReasonCerebralPalsy() !=null){
            tempForm.getAfterBirthReasonCerebralPalsy().setGeneralEvaluationForm(tempForm);
        }

        System.out.println("tempForm.getBotoxTreatment()");
        if(tempForm.getBotoxTreatment() !=null){
            tempForm.getBotoxTreatment().setGeneralEvaluationForm(tempForm);

            if( botoxImage != null){
                tempForm.getBotoxTreatment().setBotoxRecordUrl("");

                BotoxTreatment persistedBotoxTreatment = botoxTreatmentRepository.save(tempForm.getBotoxTreatment());
                String savedUrl = saveBotoxImage(persistedBotoxTreatment, botoxImage);
                persistedBotoxTreatment.setBotoxRecordUrl(savedUrl);
                tempForm.setBotoxTreatment(persistedBotoxTreatment);
            }
            System.out.println("tempForm.getBotoxTreatment()");
        }

        if(tempForm.getVisualImpairment() !=null){
            tempForm.getVisualImpairment().setGeneralEvaluationForm(tempForm);
        }

        if(tempForm.getHearingImpairment() !=null){
            tempForm.getHearingImpairment().setGeneralEvaluationForm(tempForm);
        }

        if( tempForm.getEpilepsy() !=null){
            tempForm.getEpilepsy().setGeneralEvaluationForm(tempForm);
        }

        if( tempForm.getPhysiotherapyPast() !=null){
            tempForm.getPhysiotherapyPast().setGeneralEvaluationForm(tempForm);
            if(tempForm.getPhysiotherapyPast().getPhysiotherapyCentralCollection() != null){
                tempForm.getPhysiotherapyPast().getPhysiotherapyCentralCollection().forEach(physiotherapyCentral -> physiotherapyCentral.setPhysiotherapyPast(tempForm.getPhysiotherapyPast()));
            }
        }
    }


    private void setManyToManyBidirectional(GeneralEvaluationForm tempForm){

        if(tempForm.getAppliedSurgeryCollection() !=null){
            for(AppliedSurgery a:tempForm.getAppliedSurgeryCollection()){

                appliedSurgeryRepository.save(a);
            }
        }


        if(tempForm.getCoexistingDiseasesCollection() !=null){
            for(CoexistingDiseases a:tempForm.getCoexistingDiseasesCollection()){
                coexistingDiseasesRepository.save(a);
            }
        }

    }

    private String saveBotoxImage(BotoxTreatment botoxTreatment, MultipartFile image){
        String directory = ApiPaths.SavingBotoxImagePath.CTRL + ""+securityHelper.getUsername()+botoxTreatment.getId();

        try
        {
            byte[] bytes = image.getBytes();
            Path path = Paths.get(directory );
            Files.write(path, bytes);

            return directory;
        }
        catch(Exception e)
        {
            return "error = "+e;
        }

    }

    @Override
    public Boolean save(String gefd, MultipartFile botoxImage){

        try{

        log.warn("GeneralEval. servisine girdi" );
        GeneralEvaluationFormDto tempFormDto =  objectMapper.readValue(gefd, GeneralEvaluationFormDto.class);
        GeneralEvaluationForm tempForm = modelMapper.map(tempFormDto, GeneralEvaluationForm.class);
        log.warn("GeneralEval: Mapleme başarılı" );

        setBidirectionalOneToOne(tempForm, botoxImage);
        log.warn("Gen. Ev. Form- One-To-one Bitti. servisine girdi" );

        fillExpectationsAboutProgram(tempForm.getExpectationsAboutProgramCollection(),tempForm);
        fillOrthesisInfoCollection(tempForm.getOrthesisInfoCollection(),tempForm);
        fillOtherOrthesisInfo(tempForm.getOtherOrthesisInfoCollection(),tempForm);
        fillUsedMedicine(tempForm.getUsedMedicineCollection(),tempForm);
        log.warn("Gen. Ev. Form- Many-to-One Bitti. servisine girdi" );


        setManyToManyBidirectional(tempForm);
        log.warn("Gen. Ev. Form- Many-to-Many Bitti. servisine girdi" );


        tempForm.setPatient(patientRepository.getPatientByUser(userRepository.findByUsername(securityHelper.getUsername())));
        log.warn("Son kayit islemi : ");


        generalEvaluationFormRepository.save(tempForm);
        return Boolean.TRUE;


        } catch (Exception e) {
            log.error("REGISTRATION Failed=>", e);
            return Boolean.FALSE;
        }
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
