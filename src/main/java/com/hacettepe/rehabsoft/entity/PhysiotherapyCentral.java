package com.hacettepe.rehabsoft.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "physiotherapy_central")
@Getter
@Setter
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
