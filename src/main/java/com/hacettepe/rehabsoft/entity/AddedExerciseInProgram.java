package com.hacettepe.rehabsoft.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "added_exercise_in_program")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "idgen", sequenceName = "added_exercise_in_program_seq", initialValue = 1, allocationSize = 1)
public class AddedExerciseInProgram extends BaseEntity  {

    @ManyToOne
    @JoinColumn(name = "physiotherapy_program_id")
    private PhysiotherapyProgram physiotherapyProgram;

    @ManyToOne
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    @OneToMany(mappedBy = "addedExerciseInProgram", cascade = CascadeType.REMOVE)
    private Collection<ScheduledExercise> scheduledExerciseCollection;
}
