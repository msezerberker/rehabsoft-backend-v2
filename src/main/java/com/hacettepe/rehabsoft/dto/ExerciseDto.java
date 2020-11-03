package com.hacettepe.rehabsoft.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Collection;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Data Transfer Object for Exercise")
public class ExerciseDto {

    @ApiModelProperty(required = true,value = "ID")
    private Long id;

    @ApiModelProperty(required = true, value= "ExerciseName")
    private String exerciseName;

    @ApiModelProperty(required = true, value= "ExerciseContent")
    private String exerciseContent;

    @ApiModelProperty(required = true, value = "ExerciseImages")
    private Collection<ExerciseImageDto> exerciseImageCollection;

    @ApiModelProperty(required = true, value = "ExerciseVideos")
    private Collection<ExerciseVideoDto> exerciseVideoCollection;
}
