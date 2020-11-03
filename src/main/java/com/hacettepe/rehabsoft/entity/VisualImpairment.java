package com.hacettepe.rehabsoft.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "visual_impairment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "idgen", sequenceName = "visual_impairment_seq", initialValue = 1, allocationSize = 1)

public class VisualImpairment extends BaseEntity{

    @Column(name = "information")
    private String information;

    @OneToOne
    @JoinColumn( name="general_evaluation_form_id")
    private GeneralEvaluationForm generalEvaluationForm;
}