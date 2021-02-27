package com.hacettepe.rehabsoft.repository;

import com.hacettepe.rehabsoft.entity.ResponseVideoRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ResponseVideoRequestRepository extends JpaRepository<ResponseVideoRequest,Long> {
    @Query("select r from ResponseVideoRequest r where r.videoRequest.patient.tcKimlikNo = ?1")
    List<ResponseVideoRequest> getResponseVideoRequestsByPatientTcKimlikNo(String tcKimlikNo);
}
