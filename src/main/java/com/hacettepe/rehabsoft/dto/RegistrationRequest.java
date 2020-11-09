package com.hacettepe.rehabsoft.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class RegistrationRequest {
    //Register olurken istenen bilgilerin DTOsu

    @NotNull
    @NotEmpty
    private String firstName;

    @NotNull
    @NotEmpty
    private String surname;

    @NotNull
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
    private String password;

    //private String passwordconfirmPassword;
    @Column(unique = true)
    @NotNull
    @NotEmpty
    private String email;
}
