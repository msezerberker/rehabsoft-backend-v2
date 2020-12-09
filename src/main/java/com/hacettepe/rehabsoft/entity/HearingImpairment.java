package com.hacettepe.rehabsoft.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "hearing_impairment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "idgen", sequenceName = "hearing_impairment_seq", initialValue = 1, allocationSize = 1)
public class HearingImpairment extends BaseEntity{

    @Column(name = "is_use_hearing_aid")
    private Boolean isUseHearingAid;

    @JsonBackReference
    @OneToOne
    @JoinColumn( name="general_evaluation_form_id")
    private GeneralEvaluationForm generalEvaluationForm;
}
