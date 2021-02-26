package com.hacettepe.rehabsoft.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;

public class UserCrudDto {

    private String firstName;

    private String surname;

    private String email;

    private Collection<NotificationDto> notificationCollection;

}
