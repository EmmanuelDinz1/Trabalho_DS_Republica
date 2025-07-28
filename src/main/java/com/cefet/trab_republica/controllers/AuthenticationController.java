// src/main/java/com/cefet/trab_republica/controllers/AuthenticationController.java
package com.cefet.trab_republica.controllers;

import com.cefet.trab_republica.dto.auth.AuthenticationDTO;
import com.cefet.trab_republica.dto.auth.LoginResponseDTO;
import com.cefet.trab_republica.dto.auth.RegisterDTO;
import com.cefet.trab_republica.entities.Morador;
import com.cefet.trab_republica.repositories.MoradorRepository;
import com.cefet.trab_republica.services.TokenService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private MoradorRepository moradorRepository;
    @Autowired private TokenService tokenService;
    @Autowired private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO data) {
        var authToken = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        try {
            var auth = authenticationManager.authenticate(authToken);
            Morador m = (Morador) auth.getPrincipal();
            String jwt = tokenService.generateToken(m);
            return ResponseEntity.ok(new LoginResponseDTO(jwt, m.getId(), m.getNome()));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(401).body("Credenciais inválidas");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO data) {
        if (moradorRepository.findByEmail(data.email()) != null) {
            return ResponseEntity.badRequest().body("Erro: E-mail já está em uso!");
        }
        Morador novo = new Morador(
            data.nome(),
            data.cpf(),
            data.dataNascimento(),
            data.celular(),
            data.email(),
            data.contatosFamilia(),
            passwordEncoder.encode(data.senha()),
            data.role()
        );
        moradorRepository.save(novo);
        return ResponseEntity.ok("Usuário registrado com sucesso");
    }
}
