package com.hacettepe.rehabsoft.service;


import com.hacettepe.rehabsoft.dto.OnlineMeetingDto;
import com.hacettepe.rehabsoft.entity.OnlineMeeting;

import java.util.List;

public interface OnlineMeetingService {
//    List<OnlineMeetingDto> getAllByUserId();

    boolean save(OnlineMeetingDto onlineMeetingDto) throws Exception;

    List<OnlineMeetingDto> getOnlineMeetingsByUsername(String username) throws Exception;

    Boolean isUsernameHasOnlineMeetingInCurrentDay(String username);

//    Boolean delete(Long id);
//
//    String updateOnlineMeetingDto(OnlineMeetingDto onlineMeetingDto) throws Exception;
}
