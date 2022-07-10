package com.pickemsystem.pickemsystembackend.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pickemsystem.pickemsystembackend.dto.requests.LoginDTO;
import com.pickemsystem.pickemsystembackend.dto.responses.ApiResponseDTO;
import com.pickemsystem.pickemsystembackend.utils.AppMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final String jwtSecret;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, String jwtSecret) {
        this.authenticationManager = authenticationManager;
        this.jwtSecret = jwtSecret;
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
            throw new RuntimeException(e);
        }
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(username, password);

        return authenticationManager.authenticate(user);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String username = authResult.getPrincipal().toString();
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());

        String accessToken = JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + (10 * 60 * 1000)))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", authResult.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
        String refreshToken = JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + (30 * 60 * 1000)))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);

        ApiResponseDTO apiResponseDTO = new ApiResponseDTO();
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        apiResponseDTO.setMessage(AppMessages.SUCCESSFUL_LOGIN);
        apiResponseDTO.setData(tokens);

        new ObjectMapper().writeValue(response.getOutputStream(), apiResponseDTO);
    }
}
