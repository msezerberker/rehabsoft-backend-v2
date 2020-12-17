package com.hacettepe.rehabsoft.repository;

import com.hacettepe.rehabsoft.entity.ExerciseVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

public interface ExerciseVideoRepository extends JpaRepository<ExerciseVideo,Long> {

    @Transactional
    @Modifying
    @Query("update ExerciseVideo ev set ev.title =:title where ev.id =:id")
    void updateTitleById(@Param("title") String title, @Param("id") Long id);
}
