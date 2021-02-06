package com.hacettepe.rehabsoft.repository;

import com.hacettepe.rehabsoft.entity.Patient;
import com.hacettepe.rehabsoft.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient,Long> {

    Patient getPatientByUser(User user);
    Patient getPatientByTcKimlikNo(String tcKimlikNo);
    //Patient getPatientByUserUsername(String username);
    Patient getPatientByUser_Username(String username);
}
