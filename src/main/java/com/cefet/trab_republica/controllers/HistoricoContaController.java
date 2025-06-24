package com.cefet.trab_republica.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.cefet.trab_republica.entities.HistoricoConta;
import com.cefet.trab_republica.services.HistoricoContaService;

@RestController
@RequestMapping("/api/historicos")
public class HistoricoContaController {

    @Autowired
    private HistoricoContaService historicoService;

    @GetMapping
    public ResponseEntity<List<HistoricoConta>> getAllHistoricos() {
        return ResponseEntity.ok(historicoService.listarHistoricos());
    }

    @GetMapping("/conta/{contaId}")
    public ResponseEntity<List<HistoricoConta>> getHistoricosByConta(@PathVariable Long contaId) {
        List<HistoricoConta> historicos = historicoService.buscarHistoricoPorConta(contaId);
        return ResponseEntity.ok(historicos);
    }

    @PostMapping
    public ResponseEntity<HistoricoConta> createHistorico(@RequestBody HistoricoConta historico) {
        HistoricoConta criado = historicoService.criarHistorico(historico);
        return ResponseEntity.status(201).body(criado);
    }
}