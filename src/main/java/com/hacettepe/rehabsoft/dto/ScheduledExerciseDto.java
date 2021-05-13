package com.hacettepe.rehabsoft.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Data Transfer Object for Scheduled Exercise")
public class ScheduledExerciseDto {

    @ApiModelProperty(required = true,value = "ID")
    private Long id;

    @NotEmpty
    @ApiModelProperty(required = true,value = "addedExerciseInProgram")
    private AddedExerciseInProgramDto addedExerciseInProgram;

    @NotEmpty
    @ApiModelProperty(required = true,value = "scheduled_date")
    private LocalDateTime scheduled_date;

    @NotNull
    @ApiModelProperty(required = true,value = "isApplied")
    private boolean isApplied;
}
