package com.hacettepe.rehabsoft.service.implementations;

import com.hacettepe.rehabsoft.dto.NotificationDto;
import com.hacettepe.rehabsoft.helper.NotificationServiceHelper;
import com.hacettepe.rehabsoft.repository.NotificationRepository;
import com.hacettepe.rehabsoft.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceImp implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationServiceHelper notificationServiceHelper;

    @Override
    public List<NotificationDto> getAll() {
        return null;
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
}
