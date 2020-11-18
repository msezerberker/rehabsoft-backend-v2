package com.hacettepe.rehabsoft.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "physiotherapy_past")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "idgen", sequenceName = "physiotherapy_past_seq", initialValue = 1, allocationSize = 1)
public class PhysiotherapyPast extends BaseEntity {
    @Column(name = "number_of_weekly_session")
    private Integer numberOfWeeklySession;

    @OneToMany(mappedBy = "physiotherapyPast")
    private Collection<PhysiotherapyCentral> physiotherapyCentralCollection;
}
