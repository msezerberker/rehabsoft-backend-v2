package com.hacettepe.rehabsoft.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "form_answers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "idgen", sequenceName = "form_answers_seq", initialValue = 1, allocationSize = 1)
public class FormAnswers extends BaseEntity {

    @Column(name = "answer")
    private String answer;

    @ManyToOne
    @JoinColumn(name = "assigned_form_id")
    private AssignedForm assignedForm;

    @OneToOne
    @JoinColumn(name = "form_field_id")
    private FormField formField;

}
