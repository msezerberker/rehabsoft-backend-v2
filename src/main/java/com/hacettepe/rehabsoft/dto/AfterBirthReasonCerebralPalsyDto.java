package com.hacettepe.rehabsoft.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Data Transfer Object for AfterBirthReasonCerebralPalsy")
public class AfterBirthReasonCerebralPalsyDto {
    private Long id;
    private Integer occuringMonth;
    private String cause;
    private String causeInfo;
}
