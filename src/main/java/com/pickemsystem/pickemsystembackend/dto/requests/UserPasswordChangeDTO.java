package com.pickemsystem.pickemsystembackend.dto.requests;

import com.pickemsystem.pickemsystembackend.utils.InvalidRequestMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserPasswordChangeDTO {

    @NotNull(message = InvalidRequestMessages.NOTNULL_USERNAME)
    private String username;
    @NotNull(message = InvalidRequestMessages.NOTNULL_PASSWORD)
    private String newPassword;

}
