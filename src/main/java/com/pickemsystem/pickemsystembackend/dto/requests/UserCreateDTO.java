package com.pickemsystem.pickemsystembackend.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCreateDTO {

    @NotNull
    private String username;
    @NotNull
    private String email;
    @NotNull
    private String password;
}
