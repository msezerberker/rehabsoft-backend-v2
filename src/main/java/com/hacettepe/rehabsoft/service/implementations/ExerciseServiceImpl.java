package com.hacettepe.rehabsoft.service.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hacettepe.rehabsoft.dto.ExerciseDto;
import com.hacettepe.rehabsoft.dto.ExerciseVideoDto;
import com.hacettepe.rehabsoft.entity.*;
import com.hacettepe.rehabsoft.helper.BeanValidationDeserializer;
import com.hacettepe.rehabsoft.helper.FileOperationHelper;
import com.hacettepe.rehabsoft.helper.SecurityHelper;
import com.hacettepe.rehabsoft.repository.ExerciseImageRepository;
import com.hacettepe.rehabsoft.repository.ExerciseRepository;
import com.hacettepe.rehabsoft.repository.ExerciseVideoRepository;
import com.hacettepe.rehabsoft.repository.UserRepository;
import com.hacettepe.rehabsoft.service.ExerciseService;
import com.hacettepe.rehabsoft.util.ApiPaths;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
    private final ExerciseImageRepository exerciseImageRepository;

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
            fillExerciseVideoCollection( tempExercise, exerciseMediaList );

            tempExercise.setUser(userRepository.findByUsername(securityHelper.getUsername()));
            tempExercise = exerciseRepository.save(tempExercise);
            exerciseDto.setId(tempExercise.getId());

            return "Egzersiz başarıyla kaydedildi!";
        } catch (Exception e){
            e.printStackTrace();
            throw new Exception();
        }

    }

    @Override
    @Transactional
    public Boolean delete(Long id) {
        try{
            if(exerciseRepository.findById(id).isPresent()){
                exerciseRepository.deleteById(id);
                deleteExerciseMediaFiles(exerciseRepository.findById(id).get());
            }
            else{
                throw new Exception();
            }

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
    @Transactional
    public String updateExercise(String exerciseJSON, MultipartFile[] exerciseFiles) throws Exception {

        try {
            ExerciseDto exerciseDto = (ExerciseDto) BeanValidationDeserializer.deserializeJSONWithValidation(exerciseJSON, ExerciseDto.class);
            Exercise dbExercise = exerciseRepository.findById(exerciseDto.getId()).orElseThrow(EntityNotFoundException::new);

            if(!dbExercise.getExerciseName().equals(exerciseDto.getExerciseName()) ){
                if(this.isExerciseNameExists(exerciseDto.getExerciseName())){
                    return "Bu isim başka bir egzersiz için kullanılıyor.Lütfen başka bir isim seçiniz.";
                }
            }

            dbExercise.setExerciseName(exerciseDto.getExerciseName());
            dbExercise.setExerciseContent(exerciseDto.getExerciseContent());
            exerciseRepository.save(dbExercise);

            updateExerciseMediaCollection(exerciseFiles, dbExercise, exerciseDto);
            return "Egzersiz ile ilgili değişiklikler başarıyla kaydedildi!";

        } catch (Exception e){
            e.printStackTrace();
            throw new Exception();
        }

    }

    @Override
    public byte[] getExerciseImageById(Long id) throws IOException {
        Optional<ExerciseVideo> exerciseVideo = exerciseVideoRepository.findById(id);
        if(exerciseVideo.isPresent()){
            String path = exerciseVideo.get().getVideoUrl();
            path = FileOperationHelper.splitPathAndMergeStartFromStaticDirectory(path);
            InputStream in = getClass().getClassLoader()
                    .getResourceAsStream(path );
            return IOUtils.toByteArray(in);
        } else{
            return null;
        }
    }

    private void isFileSizeProper(MultipartFile exerciseMediaList){
        // if(exerciseMediaList.getSize()>50000)
    }

    @Transactional
    public void updateExerciseMediaCollection(MultipartFile[] exerciseFiles, Exercise exerciseFromDatabase, ExerciseDto exerciseDto) throws Exception {

        if(!exerciseDto.getExerciseVideoCollection().isEmpty()){
            cleanDeletedExerciseMedia(exerciseFromDatabase, exerciseDto);
            updateExerciseVideoNamesInCollection(exerciseDto.getExerciseVideoCollection());
            setExerciseVideoCollectionWithNewAddedExerciseVideo(exerciseFromDatabase, exerciseDto);
        }
        else{
            deleteExerciseMediaFiles(exerciseFromDatabase);
        }

        fillExerciseVideoCollection(exerciseFromDatabase, exerciseFiles);
    }

    private void setExerciseVideoCollectionWithNewAddedExerciseVideo(Exercise exerciseFromDatabase, ExerciseDto exerciseDto) {
        exerciseFromDatabase.setExerciseVideoCollection(
                exerciseDto.getExerciseVideoCollection()
                        .stream()
                        .filter(exerciseVideoDto -> exerciseVideoDto.getId() == null)
                        .map(exerciseVideoDto ->  modelMapper.map(exerciseVideoDto, ExerciseVideo.class))
                        .collect(Collectors.toList())
        );
    }

    private void updateExerciseVideoNamesInCollection(Collection<ExerciseVideoDto> exerciseVideoCollection) {
        exerciseVideoCollection.forEach(exerciseVideoDto -> exerciseVideoRepository.updateTitleById(exerciseVideoDto.getTitle(), exerciseVideoDto.getId()));
    }

    private void cleanDeletedExerciseMedia(Exercise exerciseFromDatabase, ExerciseDto exerciseDto) {
        List<ExerciseVideo> deletingExerciseVideoList = exerciseFromDatabase.getExerciseVideoCollection()
                .stream()
                .filter(exerciseVideo -> !isIdInExerciseVideoCollection(exerciseVideo, exerciseDto.getExerciseVideoCollection()) )
                .collect(Collectors.toList());

        deletingExerciseVideoList.forEach(exerciseVideo -> {
            File file = new File(exerciseVideo.getVideoUrl());
            file.delete();
        });
        exerciseFromDatabase.getExerciseVideoCollection().removeAll(deletingExerciseVideoList);
        exerciseVideoRepository.deleteAll(deletingExerciseVideoList);
    }

    private static boolean isIdInExerciseVideoCollection(ExerciseVideo exerciseVideo, Collection<ExerciseVideoDto> exerciseVideoCollectionFromDto){
        for(ExerciseVideoDto exerciseVideoDto:exerciseVideoCollectionFromDto){
            if(exerciseVideo.getId().equals(exerciseVideoDto.getId())){
                return true;
            }
        }
        return false;
    }

    private void deleteExerciseMediaFiles(Exercise exerciseFromDatabase) throws IOException {
        try{
            String folderPath = ApiPaths.ExercisePath.CTRL+"/"+exerciseFromDatabase.getExerciseName();
            FileOperationHelper.deleteDirectoryByPath(folderPath);
        } catch (Exception e){
            e.printStackTrace();
            throw new IOException();
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
                            fileType+""
                    );

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



}
