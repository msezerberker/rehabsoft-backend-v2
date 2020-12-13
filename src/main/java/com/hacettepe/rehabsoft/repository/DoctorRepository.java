package com.hacettepe.rehabsoft.repository;

import com.hacettepe.rehabsoft.entity.Doctor;
import com.hacettepe.rehabsoft.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor,Long> {

    Doctor getDoctorByUser(User user);

}
