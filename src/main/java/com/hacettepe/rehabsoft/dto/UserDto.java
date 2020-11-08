package com.hacettepe.rehabsoft.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Data Transfer Object for User")
public class UserDto {

    @ApiModelProperty(required = true,value = "ID")
    private Long id;

    @NotEmpty
    @ApiModelProperty(required = true,value = "FirstName")
    private String firstName;

    @NotEmpty
    @ApiModelProperty(required = true,value = "Surname")
    private String surname;

    @Email
    @NotEmpty
    @ApiModelProperty(required = true,value = "E-Mail")
    private String email;

}
