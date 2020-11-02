package com.hacettepe.rehabsoft.repository;

import com.hacettepe.rehabsoft.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient,Long> {
}
