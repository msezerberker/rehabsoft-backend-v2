package com.hacettepe.rehabsoft.repository;

import com.hacettepe.rehabsoft.entity.CoexistingDiseases;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoexistingDiseasesRepository extends JpaRepository<CoexistingDiseases,Long> {
}
