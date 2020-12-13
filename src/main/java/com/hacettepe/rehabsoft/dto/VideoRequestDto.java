package com.hacettepe.rehabsoft.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hacettepe.rehabsoft.entity.Doctor;
import com.hacettepe.rehabsoft.entity.Exercise;
import com.hacettepe.rehabsoft.entity.Patient;
import com.hacettepe.rehabsoft.entity.ResponseVideoRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Data Transfer Object for Video Request From Patient")
public class VideoRequestDto {


    private String requestContent;

    private String requestTitle;

    //@ManyToOne
    @JsonIgnore
    private DoctorDto doctor;

    //@ManyToOne
    @JsonIgnore
    private PatientDto patient;

    //ManyToMany
    @JsonIgnore
    private Collection<Exercise> exerciseCollection;

    //one to one
    @JsonIgnore
    private ResponseVideoRequest responseVideoRequest;

}
