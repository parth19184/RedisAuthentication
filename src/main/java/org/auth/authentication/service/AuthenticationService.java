package org.auth.authentication.service;

import lombok.RequiredArgsConstructor;
import org.auth.authentication.repository.UserRepository;
import org.auth.authentication.repository.entity.User;
import org.auth.security.JwtTokenProvider;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtService;
    private final RedisService redisService;

    public String login(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String jwtToken = jwtService.generateToken(user);

        // Save token in Redis
        redisService.saveToken(jwtToken, String.valueOf(user.getId()));

        return jwtToken;
    }

    public void logout(String token) {
        redisService.deleteToken(token);
    }
}