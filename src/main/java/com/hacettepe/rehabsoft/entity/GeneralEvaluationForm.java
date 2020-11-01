package com.hacettepe.rehabsoft.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "general_evaluation_form")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "idgen", sequenceName = "general_evaluation_form_seq", initialValue = 1, allocationSize = 1)
public class GeneralEvaluationForm extends BaseEntity {

    @Column(name = "gender")
    private String gender;

    @Column(name = "birth_date")
    private LocalDateTime birthDate;

    @Column(name = "age_as_months")
    private Integer ageAsMonth;

    @Column(name = "number_of_siblings")
    private Integer numberOfSiblings;

    @Column(name = "which_child")
    private Integer whichChild;

    @Column(name = "height_cm")
    private Integer heightCm;

    @Column(name = "weight_gr")
    private Long weightGr;

    @Column(name = "head_round_cm")
    private Integer headRoundCm;

    @Column(name = "mothers_given_birth_age_year")
    private Integer mothersGivenBirthAgeYear;

    @Column(name = "mothers_education_level")
    private String mothersEducationLevel;

    @Column(name = "type_of_pregnancy")
    private String typeOfPregnancy;

    @Column(name = "fathers_education_level")
    private String fathersEducationLevel;

    @Column(name = "multiple_pregnancy")
    private Integer multiplePregnancy;

    @Column(name = "is_relative_marriage")
    private Boolean isRelativeMarriage;

    @Column(name = "is_blood_incompatibility")
    private Boolean isBloodIncompatibility;

    @Column(name = "birth_week")
    private Integer birthWeek;

    @Column(name = "birth_type")
    private String birthType;

    @Column(name = "birth_head_around_cm")
    private Long birthHeadAroundCm;

    @Column(name = "apgar_score")
    private Integer apgarScore;

    @Column(name = "is_birth_anoxia")
    private Boolean isBirthAnoxia;

    @Column(name = "is_birth_empurpling")
    private Boolean isBirthEmpurpling;

    @Column(name = "is_high_blood_pressor_pregnancy")
    private Boolean isHighBloodPressorPregnancy;

    @Column(name = "pregnancy_infection_info")
    private String pregnancyInfectionInfo;

    @Column(name = "pregnancy_medicine_usage_info")
    private String pregnancyMedicineUsageInfo;

    @Column(name = "is_pregnancy_drinking")
    private Boolean isPregnancyDrinking;

    @Column(name = "is_pregnanyc_smoking")
    private Boolean isPregnanycSmoking;

    @Column(name = "is_oxygen_support")
    private Boolean oxygenSupport;

    @Column(name = "is_intensive_care")
    private Boolean intensiveCare;

    @OneToOne(cascade = javax.persistence.CascadeType.ALL,mappedBy = "generalEvaluationForm")
    private DiseaseOfMotherPregnancy diseaseOfMotherPregnancy;

    @OneToOne(cascade = javax.persistence.CascadeType.ALL,mappedBy = "generalEvaluationForm")
    private Hyperbilirubinemia hyperbilirubinemia;

    @OneToOne(cascade = javax.persistence.CascadeType.ALL,mappedBy = "generalEvaluationForm")
    private AfterBirthReasonCerebralPalsy afterBirthReasonCerebralPalsy;

    @OneToOne(cascade = javax.persistence.CascadeType.ALL,mappedBy = "generalEvaluationForm")
    private BotoxTreatment botoxTreatment;

    @ManyToMany
    @JoinTable(name = "applied_surgery_general_evaluation_form", joinColumns = {
            @JoinColumn(name = "general_evaluation_form_id") }, inverseJoinColumns = {
            @JoinColumn(name = "applied_surgery_id") })
    private Collection<AppliedSurgery> appliedSurgeryCollection;

    @OneToMany(mappedBy = "generalEvaluationForm", cascade = CascadeType.ALL)
    private Collection<OrthesisInfo> orthesisInfoCollection;

    @OneToMany(mappedBy = "generalEvaluationForm", cascade = CascadeType.ALL)
    private Collection<OtherOrthesisInfo> otherOrthesisInfoCollection;

    @OneToMany(mappedBy = "generalEvaluationForm", cascade = CascadeType.ALL)
    private Collection<UsedMedicine> usedMedicineCollection;

    @ManyToMany
    @JoinTable(name = "general_evaluation_form_coexisting_disease", joinColumns = {
            @JoinColumn(name = "general_evaluation_form_id") }, inverseJoinColumns = {
            @JoinColumn(name = "coexisting_disease_id") })
    private Collection<CoexistingDiseases> coexistingDiseasesCollection;

    @OneToOne(cascade = javax.persistence.CascadeType.ALL,mappedBy = "generalEvaluationForm")
    private VisualImpairment visualImpairment;

    @OneToOne(cascade = javax.persistence.CascadeType.ALL,mappedBy = "generalEvaluationForm")
    private HearingImpairment hearingImpairment;

    @OneToMany(mappedBy = "generalEvaluationForm", cascade = CascadeType.ALL)
    private Collection<ExpectationsAboutProgram> expectationsAboutProgramCollection;
}
