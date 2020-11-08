package com.hacettepe.rehabsoft.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "hyperbilirubinemia")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "idgen", sequenceName = "hyperbilirubinemia_seq", initialValue = 1, allocationSize = 1)
public class Hyperbilirubinemia extends BaseEntity {
    @OneToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn( name="general_evaluation_form_id")
    private GeneralEvaluationForm generalEvaluationForm;

    @Column(name = "is_phototeraphy")
    private Boolean isPhototeraphy;

    @Column(name = "hospital_day_time")
    private Integer hospitalDayTime;
}
