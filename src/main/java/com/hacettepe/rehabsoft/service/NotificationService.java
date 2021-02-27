package com.hacettepe.rehabsoft.service;

import com.hacettepe.rehabsoft.dto.NotificationDto;
import com.hacettepe.rehabsoft.entity.Patient;
import com.hacettepe.rehabsoft.entity.User;
import com.hacettepe.rehabsoft.entity.VideoRequest;

import java.util.List;

public interface NotificationService {
    List<NotificationDto> getAll();

    NotificationDto getById(Long id);

    String save(NotificationDto notificationDto) throws Exception;

    Boolean delete(Long id);

    String updateExercise(NotificationDto notificationDto) throws Exception;

    void createNotificationForGeneralEvaluationForm(User tempUser);

    void deleteGeneralEvaluationFormNotification(User user);

    void createNotifiactionForNewPatientToDoctor(Patient patient);

    void createNotifiactionForNewVideoRequest(VideoRequest videoRequest);
}
