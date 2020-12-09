package com.hacettepe.rehabsoft.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.hacettepe.rehabsoft.entity.*;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = " Transfer Object for General Evaluation Form")
public class GefDto {

    private String gender;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime birthDate;

    private Integer ageAsMonth;

    private Integer numberOfSiblings;

    private Integer whichChild;

    private Integer heightCm;

    private Long weightGr;

    private Integer headRoundCm;

    private Integer mothersGivenBirthAgeYear;

    private String mothersEducationLevel;

    private String typeOfPregnancy;

    private String fathersEducationLevel;

    private Integer multiplePregnancy;

    private Boolean isRelativeMarriage;

    private Boolean isBloodIncompatibility;

    private Integer birthWeek;

    private String birthType;

    private Integer birthWeight;

    private Long birthHeadAroundCm;

    private Integer apgarScore;

    private Boolean isBirthAnoxia;

    private Boolean isBirthEmpurpling;

    private Boolean isBirthCrying;

    private Boolean isHighBloodPressorPregnancy;

    private String pregnancyInfectionInfo;

    private String pregnancyMedicineUsageInfo;

    private Boolean isPregnancyDrinking;

    private Boolean isPregnanycSmoking;

    private Boolean oxygenSupport;


    private Long id;//ilk olarak bos olacak.Servisin save metodunda atama yapılacak

    private Integer intensiveCare;

    private Boolean isNewbornRetinopathy;

    private Boolean isRespiratuvarDistressSyndrom;

    private Boolean isBronchopulmonaryDysplasia;

    private Boolean isHypoglycaemia;

    //One to one
    private DiseaseOfMotherPregnancy diseaseOfMotherPregnancy;
    private Hyperbilirubinemia hyperbilirubinemia; //okey
    private AfterBirthReasonCerebralPalsy afterBirthReasonCerebralPalsy;
    private BotoxTreatment botoxTreatment;
    private VisualImpairment visualImpairment;
    private HearingImpairment hearingImpairment;
    private Epilepsy epilepsy;
    private PhysiotherapyPast physiotherapyPast;



    //One to many
    private Collection<OrthesisInfo> orthesisInfoCollection;
    private Collection<OtherOrthesisInfo> otherOrthesisInfoCollection;
    private Collection<UsedMedicine> usedMedicineCollection;
    private Collection<ExpectationsAboutProgram> expectationsAboutProgramCollection;

    //many to many
    private Collection<AppliedSurgery> appliedSurgeryCollection;

/*
    //DiseaseOfMotherPregnancy
    //private String diseaseName;
    private DiseaseOfMotherPregnancy diseaseOfMotherPregnancy;


    //Hyperbilirubinemia
    //private Boolean isPhototeraphy;
    //private Integer hospitalDayTime;
    private Hyperbilirubinemia hyperbilirubinemia;


    //AfterBirthReasonCerebralPalsy
    //private Integer occuringMonth;
    //private String cause;
    //private String causeInfo;
    private AfterBirthReasonCerebralPalsy afterBirthReasonCerebralPalsy;


    private BotoxTreatment botoxTreatment;


    private VisualImpairment visualImpairment;

    //OneTomany

    //HearingImpairment
    //private Boolean isUseHearingAid;
    private HearingImpairment hearingImpairment;

    private Epilepsy epilepsy;

    private PhysiotherapyPast physiotherapyPast;
*/
}
