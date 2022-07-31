package com.pickemsystem.pickemsystembackend.filters;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pickemsystem.pickemsystembackend.dto.responses.ApiResponseDTO;
import com.pickemsystem.pickemsystembackend.security.JWTTokenManager;
import com.pickemsystem.pickemsystembackend.utils.AppMessages;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static java.util.Arrays.stream;

public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private final JWTTokenManager jwtTokenManager;

    public CustomAuthorizationFilter(JWTTokenManager jwtTokenManager) {
        this.jwtTokenManager = jwtTokenManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals("/login")
                || request.getServletPath().equals("/registry")
                || request.getServletPath().equals("/refreshToken"))
            filterChain.doFilter(request, response);
        else {
            String autorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (autorizationHeader != null && autorizationHeader.startsWith("Bearer ")){
                String token = autorizationHeader.substring(7);

                DecodedJWT decodedJWT = null;
                try {
                    decodedJWT = jwtTokenManager.decodeJWT(token);
                } catch (TokenExpiredException e) {
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                    new ObjectMapper().writeValue(response.getOutputStream(), new ApiResponseDTO(AppMessages.EXPIRED_TOKEN));
                }

                if (decodedJWT != null){
                    String username = decodedJWT.getSubject();
                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);

                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));

                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                }
            } else
                filterChain.doFilter(request, response);
        }
    }
}
