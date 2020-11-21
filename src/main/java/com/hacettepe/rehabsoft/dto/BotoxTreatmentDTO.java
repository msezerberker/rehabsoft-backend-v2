package com.hacettepe.rehabsoft.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Data Transfer Object for Botox Treatment")
public class BotoxTreatmentDTO {

    private LocalDateTime lastBotoxDate;
    private String botoxRecordUrl;
}
