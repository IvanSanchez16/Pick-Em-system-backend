package com.pickemsystem.pickemsystembackend.dto.requests;

import com.pickemsystem.pickemsystembackend.utils.InvalidRequestMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCreateDTO {

    @NotNull(message = InvalidRequestMessages.NOTNULL_USERNAME)
    private String username;
    @NotNull(message = InvalidRequestMessages.PATTERN_EMAIL)
    @Pattern(regexp = "^[a-zA-Z\\d._%+-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,6}$", message = InvalidRequestMessages.PATTERN_EMAIL)
    private String email;
    @NotNull(message = InvalidRequestMessages.NOTNULL_PASSWORD)
    private String password;
}
