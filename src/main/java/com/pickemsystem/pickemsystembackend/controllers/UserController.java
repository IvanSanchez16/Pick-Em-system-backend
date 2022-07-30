package com.pickemsystem.pickemsystembackend.controllers;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pickemsystem.pickemsystembackend.dto.requests.UserCreateDTO;
import com.pickemsystem.pickemsystembackend.dto.responses.ApiResponseDTO;
import com.pickemsystem.pickemsystembackend.dto.responses.UserDTO;
import com.pickemsystem.pickemsystembackend.entities.ConfirmationToken;
import com.pickemsystem.pickemsystembackend.entities.User;
import com.pickemsystem.pickemsystembackend.mappers.UserMapper;
import com.pickemsystem.pickemsystembackend.services.ConfirmationTokenService;
import com.pickemsystem.pickemsystembackend.services.UserService;
import com.pickemsystem.pickemsystembackend.utils.AppMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import static java.util.Arrays.stream;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final ConfirmationTokenService confirmationTokenService;

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponseDTO> getUserById(@PathVariable(value = "userId")Long userId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        ApiResponseDTO apiResponseDTO = new ApiResponseDTO();
        Optional<User> optionalUser = userService.findById(userId);

        if (optionalUser.isPresent()){
            UserDTO userDTO = UserMapper.mapToDTO(optionalUser.get());

            apiResponseDTO.setData(userDTO);
            return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
        }

        return new ResponseEntity<>(apiResponseDTO, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    @RequestMapping("/registry")
    public ResponseEntity<ApiResponseDTO> save(@RequestBody @Valid UserCreateDTO userCreateDTO){
        ApiResponseDTO apiResponseDTO;

        if (userService.existsByEmail(userCreateDTO.getEmail())){
            apiResponseDTO = new ApiResponseDTO(AppMessages.EMAIL_TAKEN);
            return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
        }
        if (userService.existsByUsername(userCreateDTO.getUsername())){
            apiResponseDTO = new ApiResponseDTO(AppMessages.USERNAME_TAKEN);
            return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
        }

        apiResponseDTO = new ApiResponseDTO();
        User user = UserMapper.mapToEntity(userCreateDTO);
        userService.save(user);

        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setUuid(UUID.randomUUID());
        confirmationToken.setToken(UUID.randomUUID().toString());
        confirmationToken.setUser(user);
        confirmationToken.setCreatedAt(LocalDateTime.now());
        confirmationToken.setExpiredAt(LocalDateTime.now().plusMinutes(15));
        confirmationTokenService.save(confirmationToken);

        apiResponseDTO.setData(userCreateDTO);
        return new ResponseEntity<>(apiResponseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/verify/{user}")
    public ResponseEntity<ApiResponseDTO> verifyEmail(@PathVariable(name = "user") Long userId, @RequestParam String token){
        ApiResponseDTO apiResponseDTO;
        Optional<User> optionalUser = userService.findById(userId);
        if (optionalUser.isEmpty()){
            apiResponseDTO = new ApiResponseDTO(AppMessages.USER_NOT_EXISTS);
            return new ResponseEntity<>(apiResponseDTO, HttpStatus.NOT_FOUND);
        }
        User user = optionalUser.get();

        if (user.getVerifiedAt() != null){
            apiResponseDTO = new ApiResponseDTO(AppMessages.USER_ALREADY_VERIFIED);
            return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
        }

        Optional<ConfirmationToken> optionalConfirmationToken = confirmationTokenService.findByTokenAndUser(token, user.getId());
        if (optionalConfirmationToken.isEmpty()){
            apiResponseDTO = new ApiResponseDTO(AppMessages.INVALID_EMAIL_TOKEN);
            return new ResponseEntity<>(apiResponseDTO, HttpStatus.BAD_REQUEST);
        }
        ConfirmationToken confirmationToken = optionalConfirmationToken.get();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expireDateToken = confirmationToken.getExpiredAt();
        if (now.isAfter(expireDateToken)){
            apiResponseDTO = new ApiResponseDTO(AppMessages.EXPIRED_EMAIL_TOKEN);
            return new ResponseEntity<>(apiResponseDTO, HttpStatus.BAD_REQUEST);
        }

        if (!userService.verifyUser(userId)){
            apiResponseDTO = new ApiResponseDTO(AppMessages.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(apiResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        apiResponseDTO = new ApiResponseDTO(AppMessages.USER_VERIFIED);
        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response){
        String autorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (autorizationHeader != null && autorizationHeader.startsWith("Bearer ")) {
            String refreshToken = autorizationHeader.substring(7);
            String newAccessToken = userService.refreshAccessToken(request.getRequestURL().toString(), refreshToken);

            ApiResponseDTO apiResponseDTO = new ApiResponseDTO();
            Map<String, String> tokens = new HashMap<>();
            tokens.put("access_token", newAccessToken);
            tokens.put("refresh_token", refreshToken);

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            apiResponseDTO.setMessage(AppMessages.SUCCESSFUL_TOKEN_REFRESH);
            apiResponseDTO.setData(tokens);

            try {
                new ObjectMapper().writeValue(response.getOutputStream(), apiResponseDTO);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }

    }
}
