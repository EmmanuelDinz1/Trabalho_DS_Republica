package com.cefet.trab_republica.controllers;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.cefet.trab_republica.dto.LoginResponseDTO;
import com.cefet.trab_republica.dto.SaldoMoradorDTO;
import com.cefet.trab_republica.entities.Morador;
import com.cefet.trab_republica.services.MoradorService;

@RestController
@RequestMapping("/api/moradores")
public class MoradorController {

    @Autowired
    private MoradorService moradorService;

    // Injeta o AuthenticationManager configurado no SecurityConfig
    @Autowired
    private AuthenticationManager authenticationManager;
    
    // Injeta o PasswordEncoder para usarmos no cadastro
    @Autowired
    private PasswordEncoder passwordEncoder;

    // --- ENDPOINT DE AUTENTICAÇÃO CORRIGIDO ---
    @PostMapping("/auth")
    public ResponseEntity<LoginResponseDTO> authenticate(@RequestBody Map<String, String> creds) {
        String email = creds.get("email");
        String senha = creds.get("senha");
        
        try {
            // O Spring Security agora gerencia a autenticação
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, senha)
            );
            
            // Se a linha acima não lançar exceção, a autenticação foi um sucesso.
            Morador moradorAutenticado = moradorService.findByEmail(email);
            var responseDto = new LoginResponseDTO("Autenticado com sucesso!", moradorAutenticado.getId(), moradorAutenticado.getNome());
            return ResponseEntity.ok(responseDto);

        } catch (AuthenticationException e) {
            // Se a autenticação falhar, o Spring lança uma exceção.
            var responseDto = new LoginResponseDTO("Credenciais inválidas.", null, null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDto);
        }
    }

    // --- ENDPOINT DE CADASTRO CORRIGIDO ---
    @PostMapping
    public ResponseEntity<Morador> createMorador(@RequestBody Morador morador) {
        // IMPORTANTE: Codificar a senha antes de salvar!
        morador.setSenha(passwordEncoder.encode(morador.getSenha()));
        Morador criado = moradorService.cadastrarMorador(morador);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }
    
    // --- DEMAIS MÉTODOS (sem alterações) ---

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
    
    @PostMapping("/recuperar")
    public ResponseEntity<String> recuperarSenha(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        moradorService.recuperarSenha(email);
        return ResponseEntity.ok("Instruções de recuperação enviadas para o email, se cadastrado.");
    }
}