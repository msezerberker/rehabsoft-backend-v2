package com.hacettepe.rehabsoft.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "patient")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "idgen", sequenceName = "patient_seq", initialValue = 1, allocationSize = 1)
public class Patient extends BaseEntity{

    @Column(name = "tc_kimlik_no")
    private String tcKimlikNo;

    @Column(name = "address")
    private String address;

    @OneToOne
    @JoinColumn( name="general_evaluation_form_id")
    private GeneralEvaluationForm generalEvaluationForm;

    @ManyToOne
    @JoinColumn( name="users_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "parent_patient",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "parent_id"))
    private Collection<Parent> parentCollection;
}
