package com.hacettepe.rehabsoft.service;

import com.hacettepe.rehabsoft.dto.RegistrationRequest;
import com.hacettepe.rehabsoft.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto save(UserDto user);

    UserDto getByUsername(String username);

    List<UserDto> getAll();

    Boolean isUsernameExists(String username);

    Boolean isEmailExists(String email);

    Boolean deleteUser(Long id);

    Boolean register(RegistrationRequest registrationRequest);

    String updateUser(UserDto user);

}
