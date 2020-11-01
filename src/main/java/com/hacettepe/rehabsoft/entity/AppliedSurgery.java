package com.hacettepe.rehabsoft.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "applied_surgery")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "idgen", sequenceName = "applied_surgery_seq", initialValue = 1, allocationSize = 1)

public class AppliedSurgery extends BaseEntity {
    @Column(name = "surgery_name")
    private String surgeryName;

    @Column(name = "epicrisis_image_url")
    private String epicrisisImageUrl;

    @Column(name = "applying_date")
    private LocalDateTime appliyingDate;
}
