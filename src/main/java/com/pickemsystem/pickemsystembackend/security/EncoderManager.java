package com.pickemsystem.pickemsystembackend.security;

import com.pickemsystem.pickemsystembackend.config.EncodeConfig;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class EncoderManager {

    private final PasswordEncoder passwordEncoder;

    public EncoderManager(EncodeConfig encodeConfig) {
        passwordEncoder = new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2B, encodeConfig.getStrong());
    }

    public String encode(String password){
        return passwordEncoder.encode(password);
    }

    public boolean matches(String rawPassword, String encodedPassword){
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
