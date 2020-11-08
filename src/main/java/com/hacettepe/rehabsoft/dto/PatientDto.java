package com.hacettepe.rehabsoft.dto;

import com.hacettepe.rehabsoft.entity.Parent;
import com.hacettepe.rehabsoft.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.Collection;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Data Transfer Object for Patient-Form")
public class PatientDto {

    private String tcKimlikNo;
    private String address;

    // private User user; // Biz serviste login yapmıs user'ın user objesine set edecegiz

    private Collection<Parent> parentCollection;
}
