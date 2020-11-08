package com.hacettepe.rehabsoft.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.UniqueConstraint;

@Data
public class RegistrationRequest {
    //Register olurken istenen bilgilerin DTOsu

    private String firstName;

    private String surname;

    private String username;

    private String password;

    //private String passwordconfirmPassword;
    @Column(unique = true)
    private String email;
}
