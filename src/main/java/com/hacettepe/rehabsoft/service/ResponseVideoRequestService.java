package com.hacettepe.rehabsoft.service;


import com.hacettepe.rehabsoft.dto.ResponseVideoRequestDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ResponseVideoRequestService {

    String save(String responseVideoRequestJSON, MultipartFile[] responseMediaList, Long videoRequestId) throws Exception;

    List<ResponseVideoRequestDto> getAll();

    ResponseVideoRequestDto getById(Long id);

    List<ResponseVideoRequestDto> responseVideoRequestList(String tcKimlik);
}
