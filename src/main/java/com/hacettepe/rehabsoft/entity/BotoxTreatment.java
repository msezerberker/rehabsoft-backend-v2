package com.hacettepe.rehabsoft.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "botox_treatment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "idgen", sequenceName = "botox_treatment_seq", initialValue = 1, allocationSize = 1)
public class BotoxTreatment extends BaseEntity{
    @OneToOne
    @JoinColumn( name="general_evaluation_form_id")
    private GeneralEvaluationForm generalEvaluationForm;

    @Column(name = "last_botox_date")
    private LocalDateTime lastBotoxDate;

    @Column(name = "botox_record_url")
    private String botoxRecordUrl;
}
