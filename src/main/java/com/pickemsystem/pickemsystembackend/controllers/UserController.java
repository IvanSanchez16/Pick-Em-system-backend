package com.pickemsystem.pickemsystembackend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pickemsystem.pickemsystembackend.dto.requests.UserCreateDTO;
import com.pickemsystem.pickemsystembackend.dto.requests.UserPasswordChangeDTO;
import com.pickemsystem.pickemsystembackend.dto.responses.ApiResponseDTO;
import com.pickemsystem.pickemsystembackend.dto.responses.UserDTO;
import com.pickemsystem.pickemsystembackend.email.EmailSender;
import com.pickemsystem.pickemsystembackend.entities.main_entities.ConfirmationToken;
import com.pickemsystem.pickemsystembackend.entities.main_entities.PasswordResetToken;
import com.pickemsystem.pickemsystembackend.entities.main_entities.User;
import com.pickemsystem.pickemsystembackend.mappers.UserMapper;
import com.pickemsystem.pickemsystembackend.services.impl.ConfirmationTokenServiceImpl;
import com.pickemsystem.pickemsystembackend.services.TokenService;
import com.pickemsystem.pickemsystembackend.services.UserService;
import com.pickemsystem.pickemsystembackend.utils.AppMessages;
import com.pickemsystem.pickemsystembackend.utils.EmailBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final TokenService<ConfirmationToken> confirmationTokenService;

    private final TokenService<PasswordResetToken> passwordResetTokenService;

    private final EmailSender emailSender;

    @Value("${server.api.baseUrl}")
    private String apiBaseUrl;

    @Value(("${server.servlet.context-path}"))
    private String contextPath;

    @Value("${server.web.baseUrl}")
    private String webBaseUrl;

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponseDTO> getUserById(@PathVariable(value = "userId")Long userId){
        ApiResponseDTO apiResponseDTO = new ApiResponseDTO();
        Optional<User> optionalUser = userService.findById(userId);

        if (optionalUser.isPresent()){
            UserDTO userDTO = UserMapper.mapToDTO(optionalUser.get());

            apiResponseDTO.setData(userDTO);
            return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
        }

        apiResponseDTO.setMessage(AppMessages.USER_NOT_EXISTS);
        return new ResponseEntity<>(apiResponseDTO, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/registry")
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

        createAndSendEmailToken(user);

        UserDTO userDTO = UserMapper.mapToDTO(user);
        apiResponseDTO.setData(userDTO);
        return new ResponseEntity<>(apiResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/verify/{user}")
    public RedirectView verifyEmail(@PathVariable(name = "user") Long userId, @RequestParam String token){
        ApiResponseDTO apiResponseDTO;
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(webBaseUrl);

        Optional<User> optionalUser = userService.findById(userId);
        if (optionalUser.isEmpty()){
            apiResponseDTO = new ApiResponseDTO(AppMessages.USER_NOT_EXISTS);
            //return new ResponseEntity<>(apiResponseDTO, HttpStatus.NOT_FOUND);
            return redirectView;
        }
        User user = optionalUser.get();

        if (user.getVerifiedAt() != null){
            apiResponseDTO = new ApiResponseDTO(AppMessages.USER_ALREADY_VERIFIED);
            //return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
            return redirectView;
        }

        Optional<ConfirmationToken> optionalConfirmationToken = confirmationTokenService.findByTokenAndUser(token, user.getId());
        if (optionalConfirmationToken.isEmpty()){
            apiResponseDTO = new ApiResponseDTO(AppMessages.INVALID_TOKEN);
            //return new ResponseEntity<>(apiResponseDTO, HttpStatus.BAD_REQUEST);
            return redirectView;
        }
        ConfirmationToken confirmationToken = optionalConfirmationToken.get();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expireDateToken = confirmationToken.getExpiredAt();
        if (now.isAfter(expireDateToken)){
            apiResponseDTO = new ApiResponseDTO(AppMessages.EXPIRED_TOKEN);
            //return new ResponseEntity<>(apiResponseDTO, HttpStatus.BAD_REQUEST);
            return redirectView;
        }

        if (!userService.verifyUser(userId)){
            apiResponseDTO = new ApiResponseDTO(AppMessages.INTERNAL_SERVER_ERROR);
            //return new ResponseEntity<>(apiResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
            return redirectView;
        }

        confirmationTokenService.deleteByUser(userId);

        //apiResponseDTO = new ApiResponseDTO(AppMessages.USER_VERIFIED);
        //return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
        return redirectView;
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response){
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String refreshToken = authorizationHeader.substring(7);
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

    @PostMapping("/confirmation/email")
    public ResponseEntity<ApiResponseDTO> resendEmailConfirmationMail() {
        ApiResponseDTO apiResponseDTO;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> optionalUser = userService.findByUsername(auth.getPrincipal().toString());
        if (optionalUser.isEmpty()){
            apiResponseDTO = new ApiResponseDTO(AppMessages.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(apiResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        User user = optionalUser.get();
        createAndSendEmailToken(user);

        apiResponseDTO = new ApiResponseDTO(AppMessages.EMAIL_CONFIRMATION_MAIL_SENT);
        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/password/reset")
    private ResponseEntity<ApiResponseDTO> passwordReset(@RequestBody @Valid UserPasswordChangeDTO userDTO, @RequestParam String token) {
        ApiResponseDTO apiResponseDTO;

        Optional<User> optionalUser = userService.findByUsername(userDTO.getUsername());
        if (optionalUser.isEmpty()) {
            apiResponseDTO = new ApiResponseDTO(AppMessages.USER_NOT_EXISTS);
            return new ResponseEntity<>(apiResponseDTO, HttpStatus.NOT_FOUND);
        }
        User user = optionalUser.get();

        Optional<PasswordResetToken> optional = passwordResetTokenService.findByTokenAndUser(token, user.getId());
        if (optional.isEmpty()) {
            apiResponseDTO = new ApiResponseDTO(AppMessages.INVALID_TOKEN);
            return new ResponseEntity<>(apiResponseDTO, HttpStatus.BAD_REQUEST);
        }
        PasswordResetToken passwordResetToken = optional.get();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expireDateToken = passwordResetToken.getExpiredAt();
        if (now.isAfter(expireDateToken)){
            apiResponseDTO = new ApiResponseDTO(AppMessages.EXPIRED_TOKEN);
            return new ResponseEntity<>(apiResponseDTO, HttpStatus.BAD_REQUEST);
        }

        if (userService.comparePasswords(user, userDTO.getNewPassword())){
            apiResponseDTO = new ApiResponseDTO(AppMessages.REPEATED_PASSWORD);
            return new ResponseEntity<>(apiResponseDTO, HttpStatus.BAD_REQUEST);
        }

        user.setPassword(userDTO.getNewPassword());
        userService.save(user);

        passwordResetTokenService.deleteByUser(user.getId());

        apiResponseDTO = new ApiResponseDTO(AppMessages.PASSWORD_RESET);
        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/password/email")
    private ResponseEntity<ApiResponseDTO> requestPasswordReset(@RequestParam String email) {
        ApiResponseDTO apiResponseDTO;

        Optional<User> optionalUser = userService.findByEmail(email);
        if (optionalUser.isEmpty()) {
            apiResponseDTO = new ApiResponseDTO(AppMessages.EMAIL_NOT_FOUND);
            return new ResponseEntity<>(apiResponseDTO, HttpStatus.NOT_FOUND);
        }
        User user = optionalUser.get();

        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setUuid(UUID.randomUUID());
        passwordResetToken.setToken(UUID.randomUUID().toString());
        passwordResetToken.setUser(user);
        passwordResetToken.setCreatedAt(LocalDateTime.now());
        passwordResetToken.setExpiredAt(LocalDateTime.now().plusMinutes(15));
        passwordResetTokenService.save(passwordResetToken);

        // TODO - Frontend URL WIP
        String link = String.format("%s", webBaseUrl);
        emailSender.send("Password reset request.", user.getEmail(), EmailBuilder.buildPasswordResetEmail(user.getUsername(), link));

        apiResponseDTO = new ApiResponseDTO(AppMessages.EMAIL_PASSWORD_RESET_MAIL_SENT);
        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }

    private void createAndSendEmailToken(User user) {
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setUuid(UUID.randomUUID());
        confirmationToken.setToken(UUID.randomUUID().toString());
        confirmationToken.setUser(user);
        confirmationToken.setCreatedAt(LocalDateTime.now());
        confirmationToken.setExpiredAt(LocalDateTime.now().plusMinutes(15));
        confirmationTokenService.save(confirmationToken);

        String link = String.format("%s%sverify/%d?token=%s", apiBaseUrl, contextPath, user.getId(), confirmationToken.getToken());
        emailSender.send("Confirm your email.", user.getEmail(), EmailBuilder.buildConfirmationEmail(user.getUsername(), link));
    }
}
