package com.hacettepe.rehabsoft.service.implementations;

import com.hacettepe.rehabsoft.dto.PatientDetailsDto;
import com.hacettepe.rehabsoft.dto.VideoRequestDto;
import com.hacettepe.rehabsoft.entity.Patient;
import com.hacettepe.rehabsoft.entity.User;
import com.hacettepe.rehabsoft.entity.VideoRequest;
import com.hacettepe.rehabsoft.helper.SecurityHelper;
import com.hacettepe.rehabsoft.repository.DoctorRepository;
import com.hacettepe.rehabsoft.repository.PatientRepository;
import com.hacettepe.rehabsoft.repository.UserRepository;
import com.hacettepe.rehabsoft.repository.VideoRequestRepository;
import com.hacettepe.rehabsoft.service.VideoRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class VideoRequestServiceImp implements VideoRequestService {

    @Autowired
    private VideoRequestRepository videoRequestRepository;

    private final SecurityHelper securityHelper;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    @Override
    public boolean save(VideoRequestDto videoRequestDto,String patientTcNo) {
        log.warn("Video Request Save metoduna girdi");
        VideoRequest videoRequest = modelMapper.map(videoRequestDto, VideoRequest.class);
        videoRequest.setDoctor(doctorRepository.getDoctorByUser(userRepository.findByUsername(securityHelper.getUsername())));
        videoRequest.setPatient(patientRepository.getPatientByTcKimlikNo(patientTcNo));

        videoRequestRepository.save(videoRequest);

        return true;
        //diger logicleri buraya ekleyecegiz.Dönüs tipini string yapmak daha dogru olur!!!!
    }

    @Override
    public List<VideoRequestDto> getVideoRequestHistory(String patientTcNo) {

        List<VideoRequest> videoRequestList = videoRequestRepository.findAllByPatient(patientRepository.getPatientByTcKimlikNo(patientTcNo));
        List< VideoRequestDto > videoRequestDtoList=  Arrays.asList(modelMapper.map(videoRequestList, VideoRequestDto[].class));

        if(videoRequestDtoList==null){
            log.warn("Belirtilen hastaya ait video kaydı bulunmuyor");
            return null;}

        return videoRequestDtoList;
    }

    @Override
    public List<VideoRequestDto> getActiveVideoRequest(String username){
        List<VideoRequest> videoRequestList = videoRequestRepository.findAllByPatientAndResponseVideoRequestIsNull(patientRepository.getPatientByUser(userRepository.findByUsername(username)));
        List<VideoRequestDto> videoRequestDtoList = Arrays.asList((modelMapper.map(videoRequestList, VideoRequestDto[].class)));

        if(videoRequestDtoList==null){
            log.warn("Bekleyen video isteğiniz bulunmuyor.");
            return null;}
        return  videoRequestDtoList;
    }
}
