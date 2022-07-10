package com.pickemsystem.pickemsystembackend.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCreateDTO {

    private String username;
    private String email;
    private String password;
}
