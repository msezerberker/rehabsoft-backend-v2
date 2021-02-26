package com.hacettepe.rehabsoft.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserCrudDto {


    private Long id;

    private String firstName;

    private String surname;

    private String email;

}
