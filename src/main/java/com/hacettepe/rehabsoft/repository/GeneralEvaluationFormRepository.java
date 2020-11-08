package com.hacettepe.rehabsoft.repository;

import com.hacettepe.rehabsoft.entity.GeneralEvaluationForm;
import com.hacettepe.rehabsoft.entity.Patient;
import com.hacettepe.rehabsoft.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GeneralEvaluationFormRepository  extends JpaRepository<GeneralEvaluationForm,Long> {
    List<GeneralEvaluationForm> findAll();

    GeneralEvaluationForm getByPatient(Patient patient);
}
