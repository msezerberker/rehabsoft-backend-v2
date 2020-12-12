package com.hacettepe.rehabsoft.repository;

import com.hacettepe.rehabsoft.entity.ExerciseVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface ExerciseVideoRepository extends JpaRepository<ExerciseVideo,Long> {
}
