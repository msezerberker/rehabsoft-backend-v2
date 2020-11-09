package com.hacettepe.rehabsoft.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class PhoneDto {
    @NotEmpty
    @NotNull
    private String phoneNumber;
}
