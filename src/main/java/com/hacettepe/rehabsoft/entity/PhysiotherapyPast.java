package com.hacettepe.rehabsoft.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "physiotherapy_past")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "idgen", sequenceName = "physiotherapy_past_seq", initialValue = 1, allocationSize = 1)
public class PhysiotherapyPast extends BaseEntity {
    @Column(name = "number_of_weekly_session")
    private Integer numberOfWeeklySession;

    @OneToMany(mappedBy = "physiotherapyPast")
    private Collection<PhysiotherapyCentral> physiotherapyCentralCollection;


    @OneToOne
    @JoinColumn(name = "general_evaluation_form_id")
    private GeneralEvaluationForm generalEvaluationForm;
}
