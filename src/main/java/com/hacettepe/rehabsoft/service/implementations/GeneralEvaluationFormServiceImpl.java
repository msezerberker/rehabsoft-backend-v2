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

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;


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

        if(tempForm.getBotoxTreatment() !=null){

            if( botoxImage != null){
                tempForm.getBotoxTreatment().setGeneralEvaluationForm(null);
                BotoxTreatment persistedBotoxTreatment = botoxTreatmentRepository.save(tempForm.getBotoxTreatment());

                if(botoxImage.getContentType() == null){
                    throw new InternalError();
                }

                String directoryAndImage =
                        createURLWithDirectory(
                                ApiPaths.SavingBotoxImagePath.CTRL+"",
                                securityHelper.getUsername()+"",
                                persistedBotoxTreatment.getId()+"-" + botoxImage.getName(),
                                botoxImage.getContentType().substring(botoxImage.getContentType().length() - 3)+"");

                String savedUrl = saveFileByDirectory(botoxImage, directoryAndImage);
                persistedBotoxTreatment.setBotoxRecordUrl(savedUrl);

                persistedBotoxTreatment.setGeneralEvaluationForm(tempForm);
                tempForm.setBotoxTreatment(persistedBotoxTreatment);
            }
            else{
                tempForm.getBotoxTreatment().setGeneralEvaluationForm(tempForm);
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



    private void setManyToManyBidirectional(GeneralEvaluationForm tempForm, MultipartFile[] appliedSurgeryEpicrisisImages){

        if(tempForm.getAppliedSurgeryCollection() !=null){
            List<MultipartFile> epicrisisImagesList = Arrays.asList(appliedSurgeryEpicrisisImages);

            for(AppliedSurgery appliedSurgery: tempForm.getAppliedSurgeryCollection()){
                setAppliedSurgeryEpicrisisImageFromImageList(appliedSurgery, epicrisisImagesList);
                appliedSurgeryRepository.save(appliedSurgery);
            }
        }


        if(tempForm.getCoexistingDiseasesCollection() !=null){
            for(CoexistingDiseases a:tempForm.getCoexistingDiseasesCollection()){
                coexistingDiseasesRepository.save(a);
            }
        }

    }

    // This function is used to save epicrisis image and set its url to corresponding surgery
    private void setAppliedSurgeryEpicrisisImageFromImageList(AppliedSurgery appliedSurgery, List<MultipartFile> epicrisisImageList){

        if(appliedSurgery.getEpicrisisImageUrl() == null){
            return ;
        }
        if( epicrisisImageList == null){
            return ;
        }
        if( epicrisisImageList.size() ==0){
            return ;
        }

        // Url includes index number of corresponding image to find which image belongs to the applied surgery.
        int epicrisisImageIndex = Integer.parseInt(appliedSurgery.getEpicrisisImageUrl());
        appliedSurgery.setEpicrisisImageUrl(null);
        AppliedSurgery persistedAppliedSurgery = appliedSurgeryRepository.save(appliedSurgery);
        String directoryAndImage =
                createURLWithDirectory(
                        ApiPaths.SavingAppliedSurgeryPath.CTRL+"",
                        securityHelper.getUsername()+"",
                        persistedAppliedSurgery.getId()+"-" + epicrisisImageList.get(epicrisisImageIndex).getOriginalFilename(),
                        epicrisisImageList.get(epicrisisImageIndex).getContentType().substring(epicrisisImageList.get(epicrisisImageIndex).getContentType().length() - 3)+"");
        String savedUrl = saveFileByDirectory(epicrisisImageList.get(epicrisisImageIndex), directoryAndImage);
        persistedAppliedSurgery.setEpicrisisImageUrl(savedUrl);
    }

    // This function is used to create a directory in given url, and add filename its end
    private String createURLWithDirectory(String savingDirectory, String createdDirectoryName, String fileName, String fileType){
        String createdDirectoryURL = savingDirectory + createdDirectoryName;
        File file = new File(createdDirectoryURL);
        boolean bool = file.mkdir();
        return createdDirectoryURL+ "/"+fileName+"."+fileType ;
    }

    // This function is used to save any file by giving location url and its name added in url.
    private String saveFileByDirectory(MultipartFile image, String directory){
        if(image.getContentType() != null){

            log.warn("Dosya kaydetme kismina girdi. Content type: " + image.getContentType());
            try {
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
        else return null;
    }

    @Override
    public Boolean save(String gefd, MultipartFile botoxImage, MultipartFile[] epicrisisImages){

        try{

        log.warn("GeneralEval. servisine girdi" );

        // Because the DTO comes in Form Data object from Angular, the JSON stringfy to DTO mapping is required by using ObjectMapper from Jackson
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


        setManyToManyBidirectional(tempForm, epicrisisImages);
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
