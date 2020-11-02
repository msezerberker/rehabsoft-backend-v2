package com.hacettepe.rehabsoft.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "phone")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "idgen", sequenceName = "phone_seq", initialValue = 1, allocationSize = 1)
public class Phone extends BaseEntity{

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn( name="parent_id")
    private Parent parent;
}
