package com.cefet.trab_republica.controllers;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Assumindo que você criou (ou irá criar) este DTO
import com.cefet.trab_republica.dto.LoginResponseDTO; 
import com.cefet.trab_republica.dto.SaldoMoradorDTO;
import com.cefet.trab_republica.entities.Morador;
import com.cefet.trab_republica.services.MoradorService;
import org.springframework.security.crypto.password.PasswordEncoder; // <-- Ainda necessário para o cadastro

@RestController
@RequestMapping("/api/moradores")
public class MoradorController {

    @Autowired
    private MoradorService moradorService;

    // A injeção do PasswordEncoder continua útil para codificar a senha no cadastro
    @Autowired
    private PasswordEncoder passwordEncoder;

    // A injeção do InMemoryUserDetailsManager não é mais necessária aqui,
    // pois a lógica foi para o MoradorService.

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
        // IMPORTANTE: A senha continua sendo codificada aqui antes de salvar
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

    // --- ENDPOINT DE AUTENTICAÇÃO OTIMIZADO ---
    @PostMapping("/auth")
    public ResponseEntity<LoginResponseDTO> authenticate(@RequestBody Map<String, String> creds) {
        String email = creds.get("email");
        String senha = creds.get("senha");

        // A lógica de autenticação agora está encapsulada no Service
        Morador moradorAutenticado = moradorService.autenticarEObterMorador(email, senha);

        if (moradorAutenticado != null) {
            // Sucesso: Retorna 200 OK com o ID e o nome do morador
            var responseDto = new LoginResponseDTO("Autenticado com sucesso!", moradorAutenticado.getId(), moradorAutenticado.getNome());
            return ResponseEntity.ok(responseDto);
        } else {
            // Falha: Retorna 401 Unauthorized com uma mensagem clara
            var responseDto = new LoginResponseDTO("Credenciais inválidas.", null, null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDto);
        }
    }

    // Endpoint de recuperação de senha (sem alteração)
    @PostMapping("/recuperar")
    public ResponseEntity<String> recuperarSenha(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        moradorService.recuperarSenha(email);
        return ResponseEntity.ok("Instruções de recuperação enviadas para o email, se cadastrado.");
    }
}