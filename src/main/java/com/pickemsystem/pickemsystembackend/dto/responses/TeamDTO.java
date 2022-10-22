package com.pickemsystem.pickemsystembackend.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeamDTO {

    private Long id;
    private String name;
    private String imageUrl;
}
