package com.hacettepe.rehabsoft.dto;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Column;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Valid
public class RegistrationRequest {

    @NotBlank(message = "Ad alanı boş bırakılamaz")
    private String firstName;

    @NotBlank(message = "Soyad alanı boş bırakılamaz")
    private String surname;

    @NotBlank(message = "Kullanıcı adı alanı boş bırakılamaz")
    private String username;

    @NotBlank(message = "Şifre alanı boş bırakılamaz")
    private String password;

    @NotBlank(message = "E-mail alanı boş bırakılamaz")
    private String email;
}
