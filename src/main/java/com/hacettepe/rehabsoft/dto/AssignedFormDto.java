package com.hacettepe.rehabsoft.dto;

import com.hacettepe.rehabsoft.entity.FormAnswers;
import com.hacettepe.rehabsoft.entity.FormDynamic;
import com.hacettepe.rehabsoft.entity.Patient;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Data Transfer Object for Assigned Form")
public class AssignedFormDto {

    @ApiModelProperty(required = true,value = "ID")
    private Long id;

    @NotEmpty
    @ApiModelProperty(required = true, value = "formDynamic")
    private FormDynamic formDynamic;

    @NotEmpty
    @ApiModelProperty(required = true, value = "patient")
    private Patient patient;

    @NotEmpty
    @ApiModelProperty(required = true, value = "isAnswered")
    private boolean isFormAnswered;

    @ApiModelProperty(required = true, value = "formAnswersCollection")
    private Collection<FormAnswers> formAnswersCollection;

}

