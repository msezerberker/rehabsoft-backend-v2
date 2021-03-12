package com.hacettepe.rehabsoft.repository;

import com.hacettepe.rehabsoft.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Long> {


    Boolean existsByUserId(Long id);
}
