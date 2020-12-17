package com.hacettepe.rehabsoft.service.implementations;

import com.hacettepe.rehabsoft.dto.NotificationDto;
import com.hacettepe.rehabsoft.entity.GeneralEvaluationForm;
import com.hacettepe.rehabsoft.entity.Notification;
import com.hacettepe.rehabsoft.entity.Patient;
import com.hacettepe.rehabsoft.entity.User;
import com.hacettepe.rehabsoft.helper.NotificationServiceHelper;
import com.hacettepe.rehabsoft.helper.SecurityHelper;
import com.hacettepe.rehabsoft.repository.NotificationRepository;
import com.hacettepe.rehabsoft.repository.UserRepository;
import com.hacettepe.rehabsoft.service.NotificationService;
import com.hacettepe.rehabsoft.util.NotificationPaths;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceImp implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationServiceHelper notificationServiceHelper;
    private final UserRepository userRepository;
    private final SecurityHelper securityHelper;
    private final ModelMapper modelMapper;

    @Override
    public List<NotificationDto> getAll() {
        User user = userRepository.findByUsername(securityHelper.getUsername());
        List<NotificationDto> notificationDtoList = new ArrayList<>();
        notificationRepository.findByUser(user).forEach(notification -> notificationDtoList.add(modelMapper.map(notification, NotificationDto.class)));

        return notificationDtoList;
    }

    @Override
    public NotificationDto getById(Long id) {
        return null;
    }

    @Override
    public String save(NotificationDto notificationDto) throws Exception {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

    @Override
    public String updateExercise(NotificationDto notificationDto) throws Exception {
        return null;
    }

    @Override
    public void createNotificationForGeneralEvaluationForm(User user) {
        log.warn("createNotificationForGeneralEvaluationForm'a giriyor");
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setNotificationContent("Lütfen kaydı tamamlamak için değerlendirme formunu doldurunuz.");
        notification.setNotificationUrl(NotificationPaths.BASE_PATH+"/user/general-evaluation-form");
        notificationRepository.save(notification);
    }

    @Override
    public void deleteGeneralEvaluationFormNotification(User user) {
        log.warn("deleteGeneralEvaluationFormNotification'a giriyor");
        notificationRepository.deleteByNotificationContentAndUser("Lütfen kaydı tamamlamak için değerlendirme formunu doldurunuz.", user);
    }

    @Override
    public void createNotifiactionForNewPatientToDoctor(Patient patient) {
        log.warn("createNotifiactionForNewPatientToDoctor'a giriyor");
        Notification notification = new Notification();
        notification.setUser(patient.getDoctor().getUser());
        notification.setNotificationContent("Sisteme yeni hasta kaydoldu. Hastanın detaylarını görmek için tıklayın");
        notification.setNotificationUrl(NotificationPaths.BASE_PATH+"/doctor/patient-info/:"+patient.getTcKimlikNo());
        notificationRepository.save(notification);
    }

}
