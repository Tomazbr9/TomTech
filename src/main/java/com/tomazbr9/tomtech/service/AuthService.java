package com.tomazbr9.tomtech.service;

import com.tomazbr9.tomtech.config.SecurityConfiguration;
import com.tomazbr9.tomtech.dto.LoginUserRequest;
import com.tomazbr9.tomtech.dto.RecoveryJwtTokenResponse;
import com.tomazbr9.tomtech.repository.UserRepository;
import com.tomazbr9.tomtech.security.JwtTokenService;
import com.tomazbr9.tomtech.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {


    private final AuthenticationManager authenticationManager;

    private final JwtTokenService jwtTokenService;

    private final UserRepository userRepository;

    private final SecurityConfiguration securityConfiguration;

    public RecoveryJwtTokenResponse authenticateUser(LoginUserRequest request) {

        log.info("Solicitação de login para usuário: {}", request.email());

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(request.email(), request.password());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        log.info("Usuário {} logado com sucesso", request.email());

        return new RecoveryJwtTokenResponse(jwtTokenService.generateToken(userDetails));
    }
}