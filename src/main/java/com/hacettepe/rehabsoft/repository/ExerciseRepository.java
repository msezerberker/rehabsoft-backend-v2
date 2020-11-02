package com.hacettepe.rehabsoft.repository;

import com.hacettepe.rehabsoft.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise,Long> {
}
