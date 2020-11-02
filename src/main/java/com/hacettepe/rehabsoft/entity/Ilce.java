package com.hacettepe.rehabsoft.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ilce")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "idgen", sequenceName = "ilce_seq", initialValue = 1, allocationSize = 1)
public class Ilce extends BaseEntity{

    @Column(name = "ilce_name")
    private String ilceName;

    @ManyToOne
    @JoinColumn( name="il_id")
    private Il il;
}
