package com.hacettepe.rehabsoft.dto;

import com.hacettepe.rehabsoft.entity.Parent;
import com.hacettepe.rehabsoft.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Data Transfer Object for Patient-Form")
public class PatientDto {

    @NotBlank(message = "Lütfen Kimlik Numaranızı giriniz")
    private String tcKimlikNo;
    @NotBlank(message = "Lütfen adınızı giriniz")
    private String address;

    // private User user; // Biz serviste login yapmıs user'ın user objesine set edecegiz

    @NotNull(message = "Lütfen ailenizle ilgili gerekli bilgileri doldurunuz")
    private Collection<ParentDto> parentCollection;
}
