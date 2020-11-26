package com.hacettepe.rehabsoft.repository;

import com.hacettepe.rehabsoft.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor,Long> {
}
