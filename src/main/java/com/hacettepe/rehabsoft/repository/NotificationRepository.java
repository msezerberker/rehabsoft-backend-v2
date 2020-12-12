package com.hacettepe.rehabsoft.repository;

import com.hacettepe.rehabsoft.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
}
