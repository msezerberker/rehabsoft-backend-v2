package com.hacettepe.rehabsoft.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Data Transfer Object for OrthesisInfo")
public class OrthesisInfoDto {

    private Long id;
    private Boolean rightPart;
    private Boolean leftPart;
    private String orthesisName;
}
