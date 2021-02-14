package com.hacettepe.rehabsoft.dto;


import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Data Transfer Object for ExpectationsAboutProgram")
public class ExpectationsAboutProgramDto {
    private Long id;
    private String expectationContent;
}
