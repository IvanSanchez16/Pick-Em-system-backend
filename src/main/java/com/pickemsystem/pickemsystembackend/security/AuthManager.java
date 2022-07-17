package com.pickemsystem.pickemsystembackend.security;

import com.pickemsystem.pickemsystembackend.exceptions.AuthExcepcion;
import com.pickemsystem.pickemsystembackend.utils.AppMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthManager implements AuthenticationManager {

    @Autowired
    private EncoderManager encoderManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken userAuth;
        String username = authentication.getPrincipal().toString();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (encoderManager.matches(authentication.getCredentials().toString(), userDetails.getPassword())){
            userAuth = new UsernamePasswordAuthenticationToken(
                    authentication.getPrincipal(),
                    authentication.getCredentials(),
                    userDetails.getAuthorities()
            );
            userAuth.setDetails(userDetails);

            return userAuth;
        }
        throw new AuthExcepcion(AppMessages.INCORRECT_PASSWORD);
    }
}
