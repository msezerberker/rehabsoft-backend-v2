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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


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
    private final OtherOrthesisInfoRepository otherOrthesisInfoRepository;


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

    private void fillOtherOrthesisInfo(Collection<OtherOrthesisInfo> exps,GeneralEvaluationForm tempForm, MultipartFile[] otherOrthesisImages){
        if(exps!=null){
            List<OtherOrthesisInfo> forIterationOtherOrthesisInfo = new ArrayList<>(tempForm.getOtherOrthesisInfoCollection());
            List<OtherOrthesisInfo> newOtherOrthesisInfo = new ArrayList<>(tempForm.getOtherOrthesisInfoCollection());
            for(OtherOrthesisInfo otherOrthesisInfo:forIterationOtherOrthesisInfo){
                setOtherOrthesisImagesEpicrisisImageFromImageList(otherOrthesisInfo, otherOrthesisImages, tempForm, newOtherOrthesisInfo);
            }

            tempForm.setOtherOrthesisInfoCollection(null);
            tempForm.setOtherOrthesisInfoCollection(newOtherOrthesisInfo);
            for(OtherOrthesisInfo otherOrthesisInfo:newOtherOrthesisInfo){
                otherOrthesisInfo.setGeneralEvaluationForm(tempForm);
            }
        }
    }


    public void setOtherOrthesisImagesEpicrisisImageFromImageList(OtherOrthesisInfo otherOrthesisInfo, MultipartFile[] otherOrthesisImages, GeneralEvaluationForm tempForm, List<OtherOrthesisInfo> newOtherOrthesisInfo) {

        if( otherOrthesisImages == null){
            return ;
        }

        for(MultipartFile multipartFile:otherOrthesisImages){

            StringBuilder newFileName = new StringBuilder();
            String fileType = popFileTypeFromFileName(multipartFile.getOriginalFilename(), newFileName);

            if(newFileName.toString().equals(otherOrthesisInfo.getOrthesisName())){
                otherOrthesisInfo.setOrthesisUrl(null);
                otherOrthesisInfo.setGeneralEvaluationForm(null);
                tempForm.getOtherOrthesisInfoCollection().remove(otherOrthesisInfo);
                OtherOrthesisInfo persistedOrthesis = otherOrthesisInfoRepository.save(otherOrthesisInfo);

                String directoryAndImage = createURLWithDirectory(
                        ApiPaths.SavingOtherOrthesisImagePath.CTRL+"",
                        securityHelper.getUsername()+"",
                        persistedOrthesis.getId()+"-" + newFileName.toString(),
                        fileType+"");

                String savedUrl = saveFileByDirectory(multipartFile, directoryAndImage);
                persistedOrthesis.setOrthesisUrl(savedUrl);
                newOtherOrthesisInfo.add(persistedOrthesis);
            }
            else{
                otherOrthesisInfo.setGeneralEvaluationForm(tempForm);
                newOtherOrthesisInfo.add(otherOrthesisInfo);
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
                                persistedBotoxTreatment.getId()+"",
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

        if(tempForm.getDiseaseOfMotherPregnancy() !=null){
            tempForm.getDiseaseOfMotherPregnancy().setGeneralEvaluationForm(tempForm);
        }

        if(tempForm.getHyperbilirubinemia() != null){
            tempForm.getHyperbilirubinemia().setGeneralEvaluationForm(tempForm);
        }

        if(tempForm.getAfterBirthReasonCerebralPalsy() !=null){
            tempForm.getAfterBirthReasonCerebralPalsy().setGeneralEvaluationForm(tempForm);
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

        if(tempForm.getCoexistingDiseasesCollection() !=null){
            for(CoexistingDiseases a:tempForm.getCoexistingDiseasesCollection()){
                coexistingDiseasesRepository.save(a);
            }
        }

    }



    // This function is used to create a directory in given url, and add filename its end
    private String createURLWithDirectory(String savingDirectory, String createdDirectoryName, String fileName, String fileType){
        String createdDirectoryURL = savingDirectory + createdDirectoryName;
        File file = new File(createdDirectoryURL);
        boolean bool = file.mkdir();
        return createdDirectoryURL+ "/"+fileName+"."+fileType ;
    }

    // pop file type. append popped string to second parameter which is newFileName
    private String popFileTypeFromFileName(String filename, StringBuilder newFileName){
        List<String> listToGetFileType =  new LinkedList<>(Arrays.asList(Objects.requireNonNull(filename).split("\\.")));
        String fileType = listToGetFileType.remove(listToGetFileType.size()-1);
        for(String words: listToGetFileType){
            newFileName.append(words);
        }
        return fileType;
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
    @Transactional
    public Boolean save(String gefd, MultipartFile botoxImage, MultipartFile[] epicrisisImages, MultipartFile[] otherOrthesisImages){

        try{

        log.warn("GeneralEval. servisine girdi" );
        System.out.println("gefd-string: "+gefd);

        // Because the DTO comes in Form Data object from Angular, the JSON stringfy to DTO mapping is required by using ObjectMapper from Jackson
        GeneralEvaluationFormDto tempFormDto =  objectMapper.readValue(gefd, GeneralEvaluationFormDto.class);
        GeneralEvaluationForm tempForm = modelMapper.map(tempFormDto, GeneralEvaluationForm.class);
        log.warn("GeneralEval: Mapleme başarılı" );

        // Bu fonksiyonlar, iclerinde tempFormu degistirdigi icin, bunlar once calismali start
        log.warn("Gen. Ev. Form- One-To-one Bitti. servisine girdi" );
        setManyToManyBidirectional(tempForm, epicrisisImages);
        fillAppliedSurgery(tempForm.getAppliedSurgeryCollection(),tempForm, epicrisisImages);
        log.warn("Gen. Ev. Form- Many-to-Many Bitti. servisine girdi" );
        fillOtherOrthesisInfo(tempForm.getOtherOrthesisInfoCollection(),tempForm, otherOrthesisImages);
        setBidirectionalOneToOne(tempForm, botoxImage);
        // Bu fonksiyonlar, iclerinde tempFormu degistirdigi icin, bunlar once calismali end

        fillOrthesisInfoCollection(tempForm.getOrthesisInfoCollection(),tempForm);
        fillExpectationsAboutProgram(tempForm.getExpectationsAboutProgramCollection(),tempForm);
        fillUsedMedicine(tempForm.getUsedMedicineCollection(),tempForm);
        log.warn("Gen. Ev. Form- Many-to-One Bitti. servisine girdi" );




        tempForm.setPatient(patientRepository.getPatientByUser(userRepository.findByUsername(securityHelper.getUsername())));
        log.warn("Son kayit islemi : ");


        generalEvaluationFormRepository.save(tempForm);
        return Boolean.TRUE;


        } catch (Exception e) {
            log.error("REGISTRATION Failed=>", e);
            return Boolean.FALSE;
        }
    }


    public void fillAppliedSurgery( Collection<AppliedSurgery> appliedSurgeryCollection, GeneralEvaluationForm tempForm, MultipartFile[] appliedSurgeryEpicrisisImages ) {

        if(appliedSurgeryCollection!=null){

            List<AppliedSurgery> forIterationAppliedSurgery = new ArrayList<>(tempForm.getAppliedSurgeryCollection());
            List<AppliedSurgery> newAppliedSurgeryInfo = new ArrayList<>(tempForm.getAppliedSurgeryCollection());

            for(AppliedSurgery appliedSurgery:forIterationAppliedSurgery){
                setAppliedSurgeryEpicrisisImageFromImageList(appliedSurgery, appliedSurgeryEpicrisisImages, tempForm, newAppliedSurgeryInfo);
            }

            tempForm.setAppliedSurgeryCollection(null);
            tempForm.setAppliedSurgeryCollection(newAppliedSurgeryInfo);
            for(AppliedSurgery appliedSurgery:newAppliedSurgeryInfo){
                appliedSurgery.setGeneralEvaluationForm(tempForm);
            }

        }
    }

    // This function is used to save epicrisis image and set its url to corresponding surgery
    private void setAppliedSurgeryEpicrisisImageFromImageList(AppliedSurgery appliedSurgery, MultipartFile[] epicrisisImageList, GeneralEvaluationForm tempForm, List<AppliedSurgery> newAppliedSurgeryCollection){

        if( epicrisisImageList == null){
            return ;
        }

        for(MultipartFile multipartFile:epicrisisImageList){

            StringBuilder newFileName = new StringBuilder();
            String fileType = popFileTypeFromFileName(multipartFile.getOriginalFilename(), newFileName);

            if(newFileName.toString().equals(appliedSurgery.getSurgeryName())){
                appliedSurgery.setEpicrisisImageUrl(null);
                appliedSurgery.setGeneralEvaluationForm(null);
                tempForm.getAppliedSurgeryCollection().remove(appliedSurgery);
                AppliedSurgery persistedAppliedSurgery = appliedSurgeryRepository.save(appliedSurgery);

                String directoryAndImage = createURLWithDirectory(
                        ApiPaths.SavingAppliedSurgeryImagePath.CTRL+"",
                        securityHelper.getUsername()+"",
                        persistedAppliedSurgery.getId()+"-" + newFileName.toString(),
                        fileType+"");

                String savedUrl = saveFileByDirectory(multipartFile, directoryAndImage);
                persistedAppliedSurgery.setEpicrisisImageUrl(savedUrl);
                newAppliedSurgeryCollection.add(persistedAppliedSurgery);
            }
            else{
                appliedSurgery.setGeneralEvaluationForm(tempForm);
                newAppliedSurgeryCollection.add(appliedSurgery);
            }


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
