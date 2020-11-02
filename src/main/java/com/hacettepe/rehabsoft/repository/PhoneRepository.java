package com.hacettepe.rehabsoft.repository;

import com.hacettepe.rehabsoft.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepository extends JpaRepository<Phone,Long> {
}
