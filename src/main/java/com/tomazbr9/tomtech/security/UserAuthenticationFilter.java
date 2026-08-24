package com.tomazbr9.tomtech.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.tomazbr9.tomtech.entity.User;
import com.tomazbr9.tomtech.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class UserAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        /*
         * Requisições OPTIONS são utilizadas pelo CORS (preflight).
         * Elas não devem exigir autenticação.
         */
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = recoveryToken(request);

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            authenticateUser(token);
        } catch (JWTVerificationException e) {
            SecurityContextHolder.clearContext();

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("""
                    {
                        "status": 401,
                        "error": "Unauthorized",
                        "message": "Token inválido ou expirado."
                    }
                    """);
            return;

        } catch (UsernameNotFoundException e) {
            SecurityContextHolder.clearContext();

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("""
                    {
                        "status": 401,
                        "error": "Unauthorized",
                        "message": "Usuário não encontrado."
                    }
                    """);
            return;
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Valida o JWT, recupera o usuário e cria a autenticação
     * que será armazenada no SecurityContext do Spring Security.
     */
    private void authenticateUser(String token) {

        String subject = jwtTokenService.getSubjectFromToken(token);

        User user = userRepository.findByEmail(subject)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Usuário não encontrado com email: " + subject
                        )
                );

        UserDetailsImpl userDetails = new UserDetailsImpl(user);

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);
    }

    /**
     * Recupera o JWT do header:
     *
     * Authorization: Bearer <token>
     */
    private String recoveryToken(HttpServletRequest request) {

        String authorizationHeader =
                request.getHeader("Authorization");

        if (authorizationHeader == null) {
            return null;
        }

        if (!authorizationHeader.startsWith("Bearer ")) {
            return null;
        }

        String token = authorizationHeader.substring(7).trim();

        return token.isBlank() ? null : token;
    }
}

