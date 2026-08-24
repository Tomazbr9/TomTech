package com.tomazbr9.tomtech.controller;

import com.tomazbr9.tomtech.dto.LoginUserRequest;
import com.tomazbr9.tomtech.dto.RecoveryJwtTokenResponse;
import com.tomazbr9.tomtech.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/login")
    public ResponseEntity<RecoveryJwtTokenResponse> authenticatedUser(@RequestBody LoginUserRequest request){
        RecoveryJwtTokenResponse response = service.authenticateUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
