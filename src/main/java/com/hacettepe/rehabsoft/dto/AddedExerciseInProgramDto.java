package com.hacettepe.rehabsoft.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Data Transfer Object for Added Exercise In Program")
public class AddedExerciseInProgramDto {


    @ApiModelProperty(required = true,value = "ID")
    private Long id;


    @NotEmpty
    @ApiModelProperty(required = true, value = "physiotherapyProgram")
    private PhysiotherapyProgramDto physiotherapyProgram;

    @NotEmpty
    @ApiModelProperty(required = true, value = "exercise")
    private ExerciseDto exercise;


    @ApiModelProperty(required = true, value = "scheduledExerciseCollection")
    private Collection<ScheduledExerciseDto> scheduledExerciseCollection;

}
