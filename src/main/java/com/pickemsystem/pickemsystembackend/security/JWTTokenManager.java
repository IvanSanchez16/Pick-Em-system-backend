package com.pickemsystem.pickemsystembackend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.pickemsystem.pickemsystembackend.config.JwtConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JWTTokenManager {

    private final JwtConfig jwtConfig;
    private final Algorithm algorithm;
    private final JWTVerifier verifier;

    @Autowired
    public JWTTokenManager(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
        this.algorithm = Algorithm.HMAC256(this.jwtConfig.getSecret().getBytes());
        this.verifier = JWT.require(algorithm).build();
    }

    public String generateAccessToken(Authentication auth, String requestURL){
        String username = auth.getPrincipal().toString();

        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis()
                        + ((long) jwtConfig.getDurationAccessToken() * 60000)))
                .withIssuer(requestURL)
                .withClaim("roles", auth.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .sign(algorithm);
    }

    public String generateRefreshToken(Authentication auth, String requestURL) {
        String username = auth.getPrincipal().toString();

        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() +
                        ((long) jwtConfig.getDurationRefreshToken() * 60000)))
                .withIssuer(requestURL)
                .sign(algorithm);
    }

    public DecodedJWT decodeJWT(String token){
        return verifier.verify(token);
    }
}
