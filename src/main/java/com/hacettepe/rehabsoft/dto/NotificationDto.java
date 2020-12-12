package com.hacettepe.rehabsoft.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Valid
public class NotificationDto {

    @NotNull
    @NotEmpty
    private String notificationContent;

    @NotNull
    @NotEmpty
    private String notificationUrl;

    @NotNull
    @NotEmpty
    private String notificationTitle;
}
