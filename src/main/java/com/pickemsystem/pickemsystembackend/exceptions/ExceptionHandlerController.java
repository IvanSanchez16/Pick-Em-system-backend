package com.pickemsystem.pickemsystembackend.exceptions;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.pickemsystem.pickemsystembackend.dto.responses.ApiResponseDTO;
import com.pickemsystem.pickemsystembackend.utils.AppMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    @ExceptionHandler(AuthExcepcion.class)
    public ResponseEntity<ApiResponseDTO> handleAuth(AuthExcepcion authExcepcion){
        return new ResponseEntity<>(new ApiResponseDTO(authExcepcion.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO> handleMethodArgumentNotValid(MethodArgumentNotValidException exception){
        ApiResponseDTO apiResponseDTO = new ApiResponseDTO();
        List<String> errorsList = new ArrayList<>();
        FieldError fieldError;

        for (ObjectError error:exception.getAllErrors()) {
            fieldError = (FieldError) error;
            errorsList.add(String.format("%s.%s", fieldError.getField(), error.getDefaultMessage()));
        }

        apiResponseDTO.setMessage(AppMessages.INVALID_REQUEST);
        apiResponseDTO.setData(errorsList);
        return new ResponseEntity<>(apiResponseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ApiResponseDTO> handleTokenExpired(TokenExpiredException ex){
        return new ResponseEntity<>(new ApiResponseDTO(AppMessages.EXPIRED_TOKEN), HttpStatus.UNAUTHORIZED);
    }

}
