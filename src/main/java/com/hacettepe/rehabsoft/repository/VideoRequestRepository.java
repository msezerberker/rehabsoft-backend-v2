package com.hacettepe.rehabsoft.repository;

import com.hacettepe.rehabsoft.entity.Patient;
import com.hacettepe.rehabsoft.entity.User;
import com.hacettepe.rehabsoft.entity.VideoRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoRequestRepository extends JpaRepository<VideoRequest,Long> {

    List<VideoRequest> findAllByPatient(Patient patient);
    //List<VideoRequest> getAllByPatient(Patient patient);

    List<VideoRequest> findAllByPatientAndResponseVideoRequestIsNull(Patient patient);
}
