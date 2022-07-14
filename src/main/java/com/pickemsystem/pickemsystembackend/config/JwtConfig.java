package com.pickemsystem.pickemsystembackend.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class JwtConfig {

    private String secret;
    private int durationAccessToken;
    private int durationRefreshToken;
}
