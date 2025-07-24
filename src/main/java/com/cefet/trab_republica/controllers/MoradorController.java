package com.cefet.trab_republica.controllers;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.*;

import com.cefet.trab_republica.dto.SaldoMoradorDTO;
import com.cefet.trab_republica.entities.Morador;
import com.cefet.trab_republica.services.MoradorService;

@RestController
@RequestMapping("/api/moradores")
public class MoradorController {

    @Autowired
    private MoradorService moradorService;

    // --- INJEÇÕES NECESSÁRIAS PARA AUTENTICAÇÃO ---
    @Autowired
    private InMemoryUserDetailsManager userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    // --- FIM DAS INJEÇÕES ---

    @GetMapping
    public ResponseEntity<List<Morador>> getAllMoradores() {
        return ResponseEntity.ok(moradorService.listarMoradores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Morador> getMoradorById(@PathVariable Long id) {
        Morador morador = moradorService.buscarMorador(id);
        if (morador == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(morador);
    }
    
    @GetMapping("/saldos")
    public ResponseEntity<List<SaldoMoradorDTO>> getSaldos() {
        return ResponseEntity.ok(moradorService.getSaldos());
    }

    @PostMapping
    public ResponseEntity<Morador> createMorador(@RequestBody Morador morador) {
        // IMPORTANTE: A senha deve ser codificada antes de salvar!
        morador.setSenha(passwordEncoder.encode(morador.getSenha()));
        Morador criado = moradorService.cadastrarMorador(morador);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Morador> updateMorador(@PathVariable Long id, @RequestBody Morador morador) {
        Morador atualizado = moradorService.atualizarMorador(id, morador);
        if (atualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMorador(@PathVariable Long id) {
        moradorService.deletarMorador(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/auth")
    public ResponseEntity<String> authenticate(@RequestBody Map<String, String> creds) {
        String email = creds.get("email"); // No seu caso, o username é "admin"
        String senha = creds.get("senha");

        try {
            // 1. Carrega os detalhes do usuário pelo username ("admin")
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // 2. Compara a senha enviada (em texto plano) com a senha armazenada (codificada)
            if (passwordEncoder.matches(senha, userDetails.getPassword())) {
                // Senha correta!
                // TODO: Gerar e retornar um token JWT aqui
                return ResponseEntity.ok("Autenticado com sucesso");
            } else {
                // Senha incorreta
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
            }
        } catch (UsernameNotFoundException e) {
            // Usuário não encontrado
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }
    }

    // Endpoint de recuperação de senha (stub)
    @PostMapping("/recuperar")
    public ResponseEntity<String> recuperarSenha(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        moradorService.recuperarSenha(email);
        return ResponseEntity.ok("Instruções de recuperação enviadas para o email, se cadastrado.");
    }
}