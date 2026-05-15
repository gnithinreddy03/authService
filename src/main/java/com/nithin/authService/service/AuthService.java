package com.nithin.authService.service;

import com.nithin.authService.dao.AppUser;
import com.nithin.authService.dto.AuthResponse;
import com.nithin.authService.dto.LoginRequest;
import com.nithin.authService.dto.RefreshRequest;
import com.nithin.authService.dto.RegisterRequest;
import com.nithin.authService.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Transactional
    public AuthResponse register(RegisterRequest request){
        log.info("[REGISTRATION] status = NEW username = {}", request.getUsername());
        AppUser user = new AppUser();
        user.setUsername(request.getUsername());
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );
        user.setRoles(request.getRoles());

        AppUser savedUser = userRepository.save(user);
        log.info("[REGISTRATION] status = SUCCESS username = {}", request.getUsername());
        return issueTokens(savedUser);
    }

    @Transactional
    public AuthResponse login(LoginRequest request){
        log.info("[LOGIN] status = NEW username = {}", request.getUsername());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        AppUser appUser = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow();
        log.info("[LOGIN] status = SUCCESS username = {}", request.getUsername());
        return issueTokens(appUser);
    }

    private AuthResponse issueTokens(AppUser user) {
       String accessToken = jwtService.generateAccessToken(user.getUsername(), user.getRoles());
       String refreshToken = jwtService.generateRefreshToken(user.getUsername());
       String bearer = "BEARER";
       return AuthResponse.builder()
               .accessToken(accessToken)
               .refreshToken(refreshToken)
               .tokenType(bearer)
               .build();
    }

    public AuthResponse refreshToken(RefreshRequest request) {
        String refreshToken = request.getRefreshToken();
        boolean validRefreshToken = jwtService.validateRefreshToken(refreshToken);
        if(!validRefreshToken){
            throw new RuntimeException("Invalid refresh token");//TODO:Write a custom exception
        }
        String username = jwtService.extractUsername(refreshToken);
        AppUser user = userRepository.findByUsername(username).orElseThrow();
        return issueTokens(user);
    }
}
