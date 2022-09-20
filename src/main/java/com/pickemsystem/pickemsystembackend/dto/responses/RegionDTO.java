package com.pickemsystem.pickemsystembackend.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegionDTO {

    private Integer id;
    private String name;
    private String imageURL;
    private String sport;
}
