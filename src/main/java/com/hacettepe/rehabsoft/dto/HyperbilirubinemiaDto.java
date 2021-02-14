package com.hacettepe.rehabsoft.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Data Transfer Object for Hyperbilirubinemia")
public class HyperbilirubinemiaDto {
    private Long id;
    private Boolean isPhototeraphy;
    private Integer hospitalDayTime;
}
