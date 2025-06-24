package com.cefet.trab_republica.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.cefet.trab_republica.entities.Conta;
import com.cefet.trab_republica.services.ContaService;

@RestController
@RequestMapping("/api/contas")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @GetMapping
    public ResponseEntity<List<Conta>> getAllContas() {
        return ResponseEntity.ok(contaService.listarContas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conta> getContaById(@PathVariable Long id) {
        Conta conta = contaService.buscarConta(id);
        if (conta == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(conta);
    }

    @PostMapping
    public ResponseEntity<Conta> createConta(@RequestBody Conta conta) {
        Conta criado = contaService.criarConta(conta);
        return ResponseEntity.status(201).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Conta> updateConta(@PathVariable Long id, @RequestBody Conta conta) {
        try {
            Conta atualizado = contaService.atualizarConta(id, conta);
            if (atualizado == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConta(@PathVariable Long id) {
        contaService.deletarConta(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint para replicar uma conta existente
    @PostMapping("/{id}/replicar")
    public ResponseEntity<Conta> replicateConta(@PathVariable Long id, @RequestBody Conta contaAlterada) {
        Conta copia = contaService.replicarConta(id, contaAlterada);
        if (copia == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(201).body(copia);
    }
}