package com.pickemsystem.pickemsystembackend.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pickemsystem.pickemsystembackend.dto.requests.LoginDTO;
import com.pickemsystem.pickemsystembackend.dto.responses.ApiResponseDTO;
import com.pickemsystem.pickemsystembackend.security.JWTTokenManager;
import com.pickemsystem.pickemsystembackend.utils.AppMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.pickemsystem.pickemsystembackend.exceptions.ExceptionHandlerController.logException;
import static com.pickemsystem.pickemsystembackend.utils.AppMessages.INVALID_REQUEST;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTTokenManager jwtTokenManager;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, JWTTokenManager jwtTokenManager) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenManager = jwtTokenManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        ObjectMapper mapper = new ObjectMapper();
        String username;
        String password;
        try {
            LoginDTO loginDTO = mapper.readValue(request.getInputStream(), LoginDTO.class);
            username = loginDTO.getUsername();
            password = loginDTO.getPassword();
        } catch (IOException e) {
            logException(e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            ApiResponseDTO apiResponseDTO = new ApiResponseDTO(INVALID_REQUEST);
            try {
                new ObjectMapper().writeValue(response.getOutputStream(), apiResponseDTO);
            } catch (IOException ex) {
                logException(ex);
            }
            return null;
        }

        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(user);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String accessToken = jwtTokenManager.generateAccessToken(authResult, request.getRequestURL().toString());
        String refreshToken = jwtTokenManager.generateRefreshToken(authResult, request.getRequestURL().toString());

        ApiResponseDTO apiResponseDTO = new ApiResponseDTO();
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        apiResponseDTO.setMessage(AppMessages.SUCCESSFUL_LOGIN);
        apiResponseDTO.setData(tokens);

        new ObjectMapper().writeValue(response.getOutputStream(), apiResponseDTO);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ApiResponseDTO apiResponseDTO = new ApiResponseDTO(failed.getMessage());

        new ObjectMapper().writeValue(response.getOutputStream(), apiResponseDTO);
    }
}
