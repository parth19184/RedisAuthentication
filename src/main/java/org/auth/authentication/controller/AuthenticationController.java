package org.auth.authentication.controller;

import lombok.RequiredArgsConstructor;
import org.auth.authentication.model.AuthRequest;
import org.auth.authentication.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        //todo: add assertions here.
        String token = authenticationService.login(authRequest.getUsername(), authRequest.getPassword());
        return ResponseEntity.ok().body(token);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            authenticationService.logout(token);
            return ResponseEntity.ok().body("Logged out successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid Authorization header");
        }
    }
}