package com.hacettepe.rehabsoft.dto;

import com.hacettepe.rehabsoft.entity.ResponseVideoRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
public class RequestedVideoDto {

    @ApiModelProperty(required = true,value = "ID")
    private Long id;

    @NotNull
    private String videoUrl;
}
