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
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    @ExceptionHandler(AuthExcepcion.class)
    public ResponseEntity<ApiResponseDTO> handleAuth(AuthExcepcion authExcepcion){
        return new ResponseEntity<>(new ApiResponseDTO(authExcepcion.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponseDTO> handleBadRequest(BadRequestException badRequestException) {
        return new ResponseEntity<>(new ApiResponseDTO(badRequestException.getMessage()), HttpStatus.BAD_REQUEST);
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

    public static void logException(Exception exception){
        String[] classSplit = exception.getClass().getName().split("\\.");
        String clase = classSplit[classSplit.length-1];

        log.error("---------------------------------------------------");
        log.error(String.format("%s: %s", clase,exception.getMessage()));
        // Get exception origin
        StackTraceElement[] st =
                Arrays.stream(exception.getStackTrace())
                        .filter(stackTraceElement -> stackTraceElement.getClassName().startsWith("com.pickemsystem"))
                        .toArray(StackTraceElement[]::new);

        int traceCount = Math.min(3, st.length);
        for (int i = 0; i < traceCount; i++)
            log.error(String.format("Stack trace: [File: %s | Method: %s | Line: %d]",
                    st[i].getFileName(), st[i].getMethodName(), st[i].getLineNumber()));
        log.error("---------------------------------------------------");
    }
}
