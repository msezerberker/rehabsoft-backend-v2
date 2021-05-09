package com.hacettepe.rehabsoft.controller;


import com.hacettepe.rehabsoft.helper.ResponseMessage;
import com.hacettepe.rehabsoft.service.UserService;
import com.hacettepe.rehabsoft.util.ApiPaths;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@CrossOrigin(origins = ApiPaths.LOCAL_CLIENT_BASE_PATH, maxAge = 3600)
@RequestMapping(ApiPaths.PasswordPath.CTRL)
@RestController
@Api(value = "/api/password")
public class PasswordController {

    private final JavaMailSender mailSender;

    private final UserService userService;
    private final ResponseMessage responseMessage;

    public PasswordController(JavaMailSender mailSender, UserService userService,ResponseMessage responseMessage){
        this.mailSender = mailSender;
        this.userService = userService;
        this.responseMessage = responseMessage;
    }





    @RequestMapping(value = "/forgot_password", method = RequestMethod.POST)
    public String processForgotPassword(@Valid @RequestBody String email) {

        return "basarili";
    }



    public void sendEmail(){

    }



    @RequestMapping(value = "/reset_password", method = RequestMethod.GET)
    public String showResetPasswordForm() {

        return "";
    }



    @RequestMapping(value = "/reset_password", method = RequestMethod.POST)
    public String processResetPassword() {
        return "";
    }




}
