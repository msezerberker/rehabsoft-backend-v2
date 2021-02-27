package com.hacettepe.rehabsoft.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Data Transfer Object for PhysiotherapyPast")
public class PhysiotherapyPastDto {
    private Long id;
    private Integer numberOfWeeklySession;
    private Collection<PhysiotherapyCentralDto> physiotherapyCentralCollection;
}
