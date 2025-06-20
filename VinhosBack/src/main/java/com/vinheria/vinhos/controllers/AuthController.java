package com.vinheria.vinhos.controllers;

import com.vinheria.vinhos.dto.CredenciaisDTO;
import com.vinheria.vinhos.security.JwtProvider;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtProvider jwt;

    public AuthController(AuthenticationManager authManager, JwtProvider jwt) {
        this.authManager = authManager;
        this.jwt = jwt;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody CredenciaisDTO cred) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(cred.email(), cred.senha()));
        String token = jwt.gerarToken((UserDetails) auth.getPrincipal());
        return Map.of("token", token);
    }
}
