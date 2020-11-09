package com.hacettepe.rehabsoft.dto;

import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
public class ParentDto {
    @NotNull
    @NotEmpty
    private String firstname;

    @NotNull
    @NotEmpty
    private String surname;

    @NotNull
    @NotEmpty
    private String email;

    @NotNull
    @NotEmpty
    private String parentType;

    @NotNull
    private Collection<PhoneDto> phoneCollection;
}
