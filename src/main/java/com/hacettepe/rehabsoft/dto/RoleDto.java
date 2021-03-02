package com.hacettepe.rehabsoft.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hacettepe.rehabsoft.entity.User;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Collection;

@Data
public class RoleDto {


    private String name;

    private Collection<UserDto> users;
}
