package com.hacettepe.rehabsoft.dto;

import com.hacettepe.rehabsoft.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Data Transfer Object for Exercise")
public class ExerciseDto {

    @ApiModelProperty(required = true,value = "ID")
    private Long id;

    @NotEmpty
    @ApiModelProperty(required = true, value= "ExerciseName")
    private String exerciseName;

    @Size(max = 256)
    @NotEmpty
    @ApiModelProperty(required = true, value= "ExerciseContent")
    private String exerciseContent;

    @ApiModelProperty(required = true, value = "ExerciseImages")
    private Collection<ExerciseImageDto> exerciseImageCollection;

    @ApiModelProperty(required = true, value = "ExerciseVideos")
    private Collection<ExerciseVideoDto> exerciseVideoCollection;

    @ApiModelProperty(required = true, value = "ExerciseOwner")
    private UserDto user;
}
