package com.hacettepe.rehabsoft.dto;

import com.hacettepe.rehabsoft.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Data Transfer Object for Message")
public class MessageDto {

    private String messageContent;

    private String messageTitle;

    private String receiverEmail;
    //private User senderUser;

    //private User receiverUser;


}
