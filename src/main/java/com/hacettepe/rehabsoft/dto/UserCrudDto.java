package com.hacettepe.rehabsoft.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hacettepe.rehabsoft.entity.Notification;
import com.hacettepe.rehabsoft.entity.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCrudDto {

    private Long id;

    private String username;

    private String firstName;

    private String surname;

    private String email;


}
