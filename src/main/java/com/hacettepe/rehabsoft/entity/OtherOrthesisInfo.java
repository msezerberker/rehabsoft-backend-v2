package com.hacettepe.rehabsoft.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "other_orthesis_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "idgen", sequenceName = "other_orthesis_info_seq", initialValue = 1, allocationSize = 1)
public class OtherOrthesisInfo extends BaseEntity{

    @Column(name = "orthesis_name")
    private String orthesisName;

    @JsonBackReference
    @ManyToOne
    @JoinColumn( name="general_evaluation_form_id")
    private GeneralEvaluationForm generalEvaluationForm;
}
