package com.pickemsystem.pickemsystembackend.controllers;

import com.pickemsystem.pickemsystembackend.dto.requests.UserCreateDTO;
import com.pickemsystem.pickemsystembackend.dto.responses.ApiResponseDTO;
import com.pickemsystem.pickemsystembackend.dto.responses.UserDTO;
import com.pickemsystem.pickemsystembackend.entities.User;
import com.pickemsystem.pickemsystembackend.mappers.UserMapper;
import com.pickemsystem.pickemsystembackend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponseDTO> getUserById(@PathVariable(value = "userId")Long userId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Datos del usuario: {}", auth.getPrincipal());
        logger.info("Datos de los permisos: {}", auth.getAuthorities());
        logger.info("Esta autenticado: {}", auth.isAuthenticated());

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
    @RequestMapping("/register")
    public ResponseEntity<ApiResponseDTO> save(@RequestBody UserCreateDTO userCreateDTO){
        ApiResponseDTO apiResponseDTO = new ApiResponseDTO();

        User user = UserMapper.mapToEntity(userCreateDTO);

        userService.save(user);
        apiResponseDTO.setData(userCreateDTO);
        return new ResponseEntity<>(apiResponseDTO, HttpStatus.CREATED);
    }
}
