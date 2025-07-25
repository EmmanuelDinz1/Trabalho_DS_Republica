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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MoradorRepository moradorRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Morador) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
        if (this.moradorRepository.findByEmail(data.email()) != null) {
            return ResponseEntity.badRequest().body("Erro: E-mail já está em uso!");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
        Morador newMorador = new Morador(data.nome(), data.cpf(), data.dataNascimento(), data.celular(), data.email(), data.contatosFamilia(), encryptedPassword, data.role());

        this.moradorRepository.save(newMorador);

        return ResponseEntity.ok().build();
    }
}
