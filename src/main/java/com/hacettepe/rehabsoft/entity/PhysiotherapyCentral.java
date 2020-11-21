package com.hacettepe.rehabsoft.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "physiotherapy_central")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "idgen", sequenceName = "physiotherapy_central_seq", initialValue = 1, allocationSize = 1)
public class PhysiotherapyCentral extends BaseEntity {
    @Column(name = "physiotherapy_central_name")
    private String physiotherapyCentralName;

    @ManyToOne
    @JoinColumn(name = "physiotherapy_past_id")
    private PhysiotherapyPast physiotherapyPast;
}
