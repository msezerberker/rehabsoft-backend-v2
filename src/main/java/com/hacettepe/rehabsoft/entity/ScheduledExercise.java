package com.hacettepe.rehabsoft.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "scheduled_exercise")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "idgen", sequenceName = "scheduled_exercise_seq", initialValue = 1, allocationSize = 1)
public class ScheduledExercise extends BaseEntity  {

    @ManyToOne
    @JoinColumn(name = "added_exercise_in_program_id")
    private AddedExerciseInProgram addedExerciseInProgram;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "scheduled_date")
    private LocalDateTime scheduled_date;

    @Column(name = "is_applied")
    private boolean isApplied;



}
