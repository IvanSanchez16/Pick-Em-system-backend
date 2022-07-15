package com.pickemsystem.pickemsystembackend.controllers;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.pickemsystem.pickemsystembackend.dto.requests.UserCreateDTO;
import com.pickemsystem.pickemsystembackend.dto.responses.ApiResponseDTO;
import com.pickemsystem.pickemsystembackend.dto.responses.UserDTO;
import com.pickemsystem.pickemsystembackend.entities.User;
import com.pickemsystem.pickemsystembackend.mappers.UserMapper;
import com.pickemsystem.pickemsystembackend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static java.util.Arrays.stream;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

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
    public ResponseEntity<ApiResponseDTO> save(@RequestBody UserCreateDTO userCreateDTO){
        ApiResponseDTO apiResponseDTO = new ApiResponseDTO();

        User user = UserMapper.mapToEntity(userCreateDTO);

        userService.save(user);
        apiResponseDTO.setData(userCreateDTO);
        return new ResponseEntity<>(apiResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response){
//        String autorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//        if (autorizationHeader != null && autorizationHeader.startsWith("Bearer ")) {
//            try {
//                String token = autorizationHeader.substring(7);
//
//                //DecodedJWT decodedJWT = verifier.verify(token);
//                String username = decodedJWT.getSubject();
//                String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
//
//                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//                stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
//
//                UsernamePasswordAuthenticationToken authenticationToken =
//                        new UsernamePasswordAuthenticationToken(username, null, authorities);
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//            } catch (IllegalArgumentException | JWTVerificationException e) {
//                throw new RuntimeException(e);
//            }
//        } else {
//            throw new RuntimeException("Refresh token is missing");
//        }

    }
}
