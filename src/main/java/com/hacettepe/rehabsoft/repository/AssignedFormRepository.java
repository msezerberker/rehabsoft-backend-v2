package com.hacettepe.rehabsoft.repository;

import com.hacettepe.rehabsoft.entity.AssignedForm;
import com.hacettepe.rehabsoft.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AssignedFormRepository extends JpaRepository<AssignedForm,Long> {
    List<AssignedForm> findAllByPatientOrderByCreationDate(Patient patient);
   List<AssignedForm> findAllByPatientAndIsAnsweredOrderByCreationDate(Patient patient, Boolean answered);
}
