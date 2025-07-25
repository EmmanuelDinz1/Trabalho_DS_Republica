package com.cefet.trab_republica.controllers;

import com.cefet.trab_republica.dto.MoradorDTO;
import com.cefet.trab_republica.dto.SaldoMoradorDTO;
import com.cefet.trab_republica.entities.Morador;
import com.cefet.trab_republica.services.MoradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/moradores")
public class MoradorController {

    @Autowired
    private MoradorService moradorService;

    // Endpoint para listar todos os moradores (protegido)
    @GetMapping
    public ResponseEntity<List<MoradorDTO>> getAllMoradores() {
        List<Morador> moradores = moradorService.listarMoradores();
        List<MoradorDTO> dtos = moradores.stream().map(MoradorDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // Endpoint para buscar um morador por ID (protegido)
    @GetMapping("/{id}")
    public ResponseEntity<MoradorDTO> getMoradorById(@PathVariable Long id) {
        Morador morador = moradorService.buscarMorador(id);
        if (morador == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new MoradorDTO(morador));
    }
    
    // Endpoint para buscar saldos (protegido)
    @GetMapping("/saldos")
    public ResponseEntity<List<SaldoMoradorDTO>> getSaldos() {
        return ResponseEntity.ok(moradorService.getSaldos());
    }

    // Endpoint para atualizar um morador (protegido)
    @PutMapping("/{id}")
    public ResponseEntity<MoradorDTO> updateMorador(@PathVariable Long id, @RequestBody Morador morador) {
        Morador atualizado = moradorService.atualizarMorador(id, morador);
        if (atualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new MoradorDTO(atualizado));
    }

    // Endpoint para deletar um morador (protegido)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMorador(@PathVariable Long id) {
        moradorService.deletarMorador(id);
        return ResponseEntity.noContent().build();
    }
}
