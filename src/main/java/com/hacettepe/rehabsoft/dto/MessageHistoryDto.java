package com.hacettepe.rehabsoft.dto;


import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Data Transfer Object for Message/Chat History")
public class MessageHistoryDto {

    private String senderName;
    private String senderSurname;
    private String senderEmail;

    private String receiverName;
    private String receiverSurname;
    private String receiverEmail;

    private String messageTitle;
    private String messageContent;


    private LocalDateTime creationDate;
}
