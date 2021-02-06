package com.hacettepe.rehabsoft.dto;


import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Data Transfer Object for Information about Doctors and Physiotherapist")
public class DoctorInfoDto {

    private String name;
    private String surname;
    private String email;
}
