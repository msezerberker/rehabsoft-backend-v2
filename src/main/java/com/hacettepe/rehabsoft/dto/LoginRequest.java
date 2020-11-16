package com.hacettepe.rehabsoft.dto;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

//Giris sırasında front-end tarafının bize göndereceği request objesi
@Data
@Valid
public class LoginRequest {

    @NotBlank(message = "Lütfen kullanıcı adinizi giriniz")
    private String username;

    @NotBlank(message = "Lütfen sifrenizi giriniz")
    private String password;
}