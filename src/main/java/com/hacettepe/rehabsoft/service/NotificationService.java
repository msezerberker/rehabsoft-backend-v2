package com.hacettepe.rehabsoft.service;

import com.hacettepe.rehabsoft.dto.NotificationDto;

import java.util.List;

public interface NotificationService {
    List<NotificationDto> getAll();

    NotificationDto getById(Long id);

    String save(NotificationDto notificationDto) throws Exception;

    Boolean delete(Long id);

    String updateExercise(NotificationDto notificationDto) throws Exception;

}
