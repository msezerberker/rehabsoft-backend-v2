package com.hacettepe.rehabsoft.repository;

import com.hacettepe.rehabsoft.entity.ResponseVideoRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ResponseVideoRequestRepository extends JpaRepository<ResponseVideoRequest,Long> {

}
