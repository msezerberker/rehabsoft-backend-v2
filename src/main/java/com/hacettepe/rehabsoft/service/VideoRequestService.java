package com.hacettepe.rehabsoft.service;

import com.hacettepe.rehabsoft.dto.NotificationDto;
import com.hacettepe.rehabsoft.dto.VideoRequestDto;
import com.hacettepe.rehabsoft.entity.User;
import com.hacettepe.rehabsoft.entity.VideoRequest;

import java.util.List;

public interface VideoRequestService {

    boolean save(VideoRequestDto videoRequestDto,String patientTcNo);

    List<VideoRequestDto> getVideoRequestHistory(String patientTcNo);

    List<VideoRequestDto> getActiveVideoRequest(String username);

}
