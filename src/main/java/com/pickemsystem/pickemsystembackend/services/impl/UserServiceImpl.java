package com.pickemsystem.pickemsystembackend.services.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.pickemsystem.pickemsystembackend.entities.main_entities.User;
import com.pickemsystem.pickemsystembackend.repositories.UserRepository;
import com.pickemsystem.pickemsystembackend.security.EncoderManager;
import com.pickemsystem.pickemsystembackend.security.JWTTokenManager;
import com.pickemsystem.pickemsystembackend.services.UserService;
import com.pickemsystem.pickemsystembackend.utils.AppMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static java.util.Arrays.stream;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EncoderManager encoderManager;
    @Autowired
    private JWTTokenManager jwtTokenManager;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty())
            throw new UsernameNotFoundException(AppMessages.USER_NOT_EXISTS);

        User user = optionalUser.get();
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public User save(User user) {
        user.setPassword( encoderManager.encode(user.getPassword()) );
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public boolean verifyUser(Long userId) {
        return userRepository.verifyUser(LocalDateTime.now(), userId) > 0;
    }

    @Override
    public String refreshAccessToken(String requestURL, String refreshToken){
        UsernamePasswordAuthenticationToken authenticationToken;

        DecodedJWT decodedJWT = jwtTokenManager.decodeJWT(refreshToken);
        String username = decodedJWT.getSubject();

        UserDetails userDetails = loadUserByUsername(username);

        authenticationToken =
                new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        return jwtTokenManager.generateAccessToken(authenticationToken, requestURL);
    }
}
