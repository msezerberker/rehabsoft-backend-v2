package com.hacettepe.rehabsoft.controller;
import com.hacettepe.rehabsoft.dto.LoginRequest;
import com.hacettepe.rehabsoft.dto.RegistrationRequest;
import com.hacettepe.rehabsoft.dto.TokenResponse;
import com.hacettepe.rehabsoft.entity.User;
import com.hacettepe.rehabsoft.helper.RegistrationHelper;
import com.hacettepe.rehabsoft.repository.UserRepository;
import com.hacettepe.rehabsoft.security.JwtTokenUtil;
import com.hacettepe.rehabsoft.service.GeneralEvaluationFormService;
import com.hacettepe.rehabsoft.service.PatientService;
import com.hacettepe.rehabsoft.service.implementations.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/token") //Token pathine gelen bütün isteklere izin verilecek.Bunun ayarı SecurityConfig'de
@Api(value = "/api/token")
public class AccountController {


    @Autowired
    RegistrationHelper registrationHelper;

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;
    private final UserServiceImpl userService;
    private final PatientService patientService;
    private final GeneralEvaluationFormService generalEvaluationFormService;

    public AccountController(AuthenticationManager authenticationManager,JwtTokenUtil jwtTokenUtil,UserRepository userRepository,UserServiceImpl userService,PatientService patientService,GeneralEvaluationFormService generalEvaluationFormService){
        this.userService = userService;
        this.patientService = patientService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepository;
        this.generalEvaluationFormService = generalEvaluationFormService;

    }

    @RequestMapping(method = RequestMethod.POST) //LoginRequest'i DTO kısmında olusturduk.Front-end'den gelen login objesi gibi düsün
    @ApiOperation(value = "Login Operation", response = TokenResponse.class)
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest loginRequest) throws AuthenticationException {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);

        User userFromDB = userRepository.findByUsername(loginRequest.getUsername());

        boolean isPatientSaved = patientService.isPatientAlreadySaved();
        boolean isGeneralInformationSaved = generalEvaluationFormService.isGeneralEvaluationFormExist();

        //Burada sorun olabilir:Bunu yaz:         return ResponseEntity.ok(new AuthToken(token));
        return ResponseEntity.ok(new TokenResponse(loginRequest.getUsername(),userFromDB.getFirstName(), userFromDB.getSurname(), userFromDB.getRole().getName(),token,isPatientSaved,isGeneralInformationSaved));
    }



    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ApiOperation(value="Register Operation",response = Boolean.class)
    public ResponseEntity<String> register(@Valid @RequestBody RegistrationRequest registrationRequest) throws AuthenticationException {

        log.warn("Kayıt controllerına girdi");

        String validationMessage = registrationHelper.registrationValidator(registrationRequest);
        if(validationMessage!=null){
            return ResponseEntity.ok().body(validationMessage);
        }

        Boolean response = userService.register(registrationRequest);

        if(!response){
                return ResponseEntity.badRequest().body("Kayıt sırasında bir hata meydana geldi.Lütfen tekrar deneyiniz");
            }

        return ResponseEntity.ok().body("Basariyla kaydoldunuz!");










    }
}
