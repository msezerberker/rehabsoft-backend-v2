package com.hacettepe.rehabsoft.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "disease_of_mother_pregnancy")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "idgen", sequenceName = "disease_of_mother_pregnancy_seq", initialValue = 1, allocationSize = 1)
public class DiseaseOfMotherPregnancy extends BaseEntity{

    @OneToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn( name="general_evaluation_form_id")
    private GeneralEvaluationForm generalEvaluationForm;

    @Column(name = "disease_name")
    private String diseaseName;
}
