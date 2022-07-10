package com.pickemsystem.pickemsystembackend.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@AllArgsConstructor
@Data
public class ApiResponseDTO {

    private String message;
    private Object data;

    public ApiResponseDTO() {
        message = "";
    }

    public ApiResponseDTO(String message) {
        this.message = message;
        this.data = new ArrayList<>();
    }
}
