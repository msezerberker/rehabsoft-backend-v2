package com.hacettepe.rehabsoft.dto;

import com.hacettepe.rehabsoft.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "Data Transfer Object for List-Patient-Data-Grid")
@AllArgsConstructor
@NoArgsConstructor
public class PatientDetailsDto {


    @ApiModelProperty(required = true,value = "Patient Identification")
    private String tcKimlikNo;

    @ApiModelProperty(required = true,value = "Patient Identification")
    private UserDto user;


}
