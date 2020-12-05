package com.hacettepe.rehabsoft.service.implementations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.BeanDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.hacettepe.rehabsoft.dto.ExerciseDto;
import com.hacettepe.rehabsoft.entity.Exercise;
import com.hacettepe.rehabsoft.entity.ExerciseVideo;
import com.hacettepe.rehabsoft.entity.GeneralEvaluationForm;
import com.hacettepe.rehabsoft.entity.OtherOrthesisInfo;
import com.hacettepe.rehabsoft.helper.BeanValidationDeserializer;
import com.hacettepe.rehabsoft.helper.FileOperationHelper;
import com.hacettepe.rehabsoft.helper.SecurityHelper;
import com.hacettepe.rehabsoft.repository.ExerciseRepository;
import com.hacettepe.rehabsoft.repository.ExerciseVideoRepository;
import com.hacettepe.rehabsoft.repository.UserRepository;
import com.hacettepe.rehabsoft.service.ExerciseService;
import com.hacettepe.rehabsoft.util.ApiPaths;
import com.sun.jdi.request.InvalidRequestStateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service//(value = "exerciseService")
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {

    private final ModelMapper modelMapper;
    private final ExerciseRepository exerciseRepository;
    private final ExerciseVideoRepository exerciseVideoRepository;
    private final UserRepository userRepository;
    private final SecurityHelper securityHelper;
    private final ObjectMapper objectMapper;

    @Override
    public List<ExerciseDto> getAll(){
        List<Exercise> exercises = exerciseRepository.findAll();
        return  Arrays.asList(modelMapper.map(exercises, ExerciseDto[].class));
    }

    @Override
    public ExerciseDto getById(Long id){
        Exercise exercise = exerciseRepository.getOne(id);
        return modelMapper.map(exercise, ExerciseDto.class);
    }

    @Override
    @Transactional
    public String save(String exerciseJSON, MultipartFile[] exerciseMediaList) throws Exception {

        try {
            System.out.println("exerciseJSON: "+exerciseJSON);
            // Mapping JSON to Object DTO and Entity after
            ExerciseDto exerciseDto = (ExerciseDto) BeanValidationDeserializer.deserializeJSONWithValidation(exerciseJSON, ExerciseDto.class);

            if(this.isExerciseNameExists(exerciseDto.getExerciseName())){
                return "Bu isim başka bir egzersiz için kullanılıyor.Lütfen başka bir isim seçiniz.";
            }

            Exercise tempExercise = modelMapper.map(exerciseDto, Exercise.class);
            fillExerciseVideoCollection(tempExercise, exerciseMediaList );

            tempExercise.setUser(userRepository.findByUsername(securityHelper.getUsername()));
            tempExercise = exerciseRepository.save(tempExercise);
            exerciseDto.setId(tempExercise.getId());

            return "Egzersiz başarıyla kaydedildi!";
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception();
        }

    }

    private void fillExerciseVideoCollection(Exercise exercise, MultipartFile[] exerciseMediaList) throws Exception {
        try{
            if(exercise.getExerciseVideoCollection()!=null){
                List<ExerciseVideo> forIterationnewExerciseVideoCollection = new ArrayList<>(exercise.getExerciseVideoCollection());
                List<ExerciseVideo> newExerciseVideoCollection = new ArrayList<>();

                for(ExerciseVideo exerciseVideo:forIterationnewExerciseVideoCollection){
                    saveExerciseMediaAndSetExerciseMediaUrls(exerciseVideo, exerciseMediaList, exercise, newExerciseVideoCollection);
                }

                exercise.setExerciseVideoCollection(null);
                exercise.setExerciseVideoCollection(newExerciseVideoCollection);
//            for(OtherOrthesisInfo otherOrthesisInfo:newOtherOrthesisInfo){
//                otherOrthesisInfo.setGeneralEvaluationForm(tempForm);
//            }
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new Exception();
        }

    }

    private void saveExerciseMediaAndSetExerciseMediaUrls(ExerciseVideo exerciseVideo, MultipartFile[] exerciseMediaList, Exercise exercise, List<ExerciseVideo> newOtherExerciseVideoCollection)
    throws Exception {
        if( exerciseMediaList == null){
            return ;
        }

        for(MultipartFile multipartFile:exerciseMediaList){
            try {
                StringBuilder newFileName = new StringBuilder();
                String fileType = FileOperationHelper.popFileTypeFromFileName(multipartFile.getOriginalFilename(), newFileName);

                if(newFileName.toString().equals(exerciseVideo.getTitle())){
                    exerciseVideo.setVideoUrl(null);
                    exercise.getExerciseVideoCollection().remove(exerciseVideo);
                    ExerciseVideo persistedExerciseVideo = exerciseVideoRepository.save(exerciseVideo);

                    String directoryAndMediaURL = FileOperationHelper.createURLWithDirectory(
                            ApiPaths.SavingExerciseMediaPath.CTRL+"",
                            exercise.getExerciseName()+"",
                            persistedExerciseVideo.getId()+"-" + newFileName.toString(),
                            fileType+"");

                    String savedUrl = FileOperationHelper.saveFileByDirectory(multipartFile, directoryAndMediaURL);
                    persistedExerciseVideo.setVideoUrl(savedUrl);
                    persistedExerciseVideo.setExercise(exercise);
                    newOtherExerciseVideoCollection.add(persistedExerciseVideo);
                }
            }  catch(Exception e){
                e.printStackTrace();
                throw new Exception();
            }
        }
    }

    private void isFileSizeProper(MultipartFile exerciseMediaList){
       // if(exerciseMediaList.getSize()>50000)
    }

    @Override
    @Transactional
    public Boolean delete(Long id) {
        try{
            exerciseRepository.deleteById(id);
            log.warn("Egzersiz silindi=>"+ id);
            return Boolean.TRUE;
        }
        catch (Exception e) {
            log.error("Deletion of Exercise is Failed=>", e);
            return Boolean.FALSE;
        }
    }


    @Override
    public Boolean isExerciseNameExists(String name) {
        if(exerciseRepository.getByExerciseName(name) ==null){
            return false;
        }
        return true;
    }

    @Override
    public String updateExercise(String exerciseJSON, MultipartFile[] exerciseFiles) throws Exception {

        try {
            ExerciseDto exerciseDto = (ExerciseDto) BeanValidationDeserializer.deserializeJSONWithValidation(exerciseJSON, ExerciseDto.class);
            Exercise dbExercise = exerciseRepository.getOne(exerciseDto.getId());

            if(!dbExercise.getExerciseName().equals(exerciseDto.getExerciseName()) ){
                if(this.isExerciseNameExists(exerciseDto.getExerciseName())){
                    return "Bu isim başka bir egzersiz için kullanılıyor.Lütfen başka bir isim seçiniz.";
                }
            }

            dbExercise.setExerciseName(exerciseDto.getExerciseName());
            dbExercise.setExerciseContent(exerciseDto.getExerciseContent());

            exerciseRepository.save(dbExercise);
            return "Egzersiz ile ilgili değişiklikler başarıyla kaydedildi!";

        } catch (Exception e){
            e.printStackTrace();
            throw new Exception();
        }

    }


}
