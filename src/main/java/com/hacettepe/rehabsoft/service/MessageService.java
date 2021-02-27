package com.hacettepe.rehabsoft.service;

import com.hacettepe.rehabsoft.dto.MessageDto;
import com.hacettepe.rehabsoft.dto.MessageHistoryDto;
import com.hacettepe.rehabsoft.dto.VideoRequestDto;

import java.util.List;

public interface MessageService {

    boolean save(MessageDto messageDto);
    List<MessageHistoryDto> receiveMessageHistory(String email);

    //bir tane mesaj kutusu olacak.Unique olarak gönderen listesi tutulacak.Receiver, mesaja tıkladığında mesajları görecek


}
