package com.hacettepe.rehabsoft.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "epilepsy")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "idgen", sequenceName = "doctor_seq", initialValue = 1, allocationSize = 1)
public class Epilepsy extends BaseEntity{
    @Column(name = "epilepsy_situation")
    private String epilepsySituation;
}
