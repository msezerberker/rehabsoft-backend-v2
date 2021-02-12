package com.hacettepe.rehabsoft.helper;

import com.hacettepe.rehabsoft.dto.RegistrationRequest;
import com.hacettepe.rehabsoft.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RegistrationHelper {


    private final UserService userService;

    public RegistrationHelper(UserService userService) {
        this.userService = userService;
    }

    public String registrationValidator(RegistrationRequest registrationRequest){

        if(userService.isUsernameExists(registrationRequest.getUsername())){
            return "Kullanıcı adı/Kimlik Numarası zaten kullanılmakta.";
        }

        if(userService.isEmailExists(registrationRequest.getEmail())){
            return "Email zaten kullanılmakta.";
        }

        return null;


    }




}
