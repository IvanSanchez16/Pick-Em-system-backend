package com.pickemsystem.pickemsystembackend.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Data
public class TeamCreateDTO {

    @NotNull
    private String name;
    @NotNull
    private Integer regionId;
    private MultipartFile image;

}
