package com.cefet.trab_republica.controllers;

import com.cefet.trab_republica.dto.RateioDTO;
import com.cefet.trab_republica.entities.Rateio;
import com.cefet.trab_republica.services.RateioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rateios")
public class RateioController {

    @Autowired
    private RateioService rateioService;

    /** 
     * GET /api/rateios 
     * → lista todos os rateios como DTOs
     */
    @GetMapping
    public ResponseEntity<List<RateioDTO>> getAllRateios() {
        List<RateioDTO> dtos = rateioService.listarRateios()
            .stream()
            .map(RateioDTO::new)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    /**
     * GET /api/rateios/{id}
     * → retorna um rateio específico como DTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<RateioDTO> getRateioById(@PathVariable Long id) {
        Rateio rateio = rateioService.buscarRateio(id);
        if (rateio == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new RateioDTO(rateio));
    }

    /**
     * POST /api/rateios
     * → cria um novo rateio recebendo a entidade (você pode trocar aqui
     *    por um CreateRateioDTO se preferir) e retorna o DTO criado
     */
    @PostMapping
    public ResponseEntity<RateioDTO> createRateio(@RequestBody Rateio rateio) {
        Rateio criado = rateioService.criarRateio(rateio);
        return ResponseEntity.status(201).body(new RateioDTO(criado));
    }

    /**
     * PUT /api/rateios/{id}
     * → atualiza um rateio existente e retorna o DTO
     */
    @PutMapping("/{id}")
    public ResponseEntity<RateioDTO> updateRateio(
            @PathVariable Long id,
            @RequestBody Rateio rateioPayload) {

        Rateio atualizado = rateioService.atualizarRateio(id, rateioPayload);
        if (atualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new RateioDTO(atualizado));
    }

    /**
     * DELETE /api/rateios/{id}
     * → exclui um rateio
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRateio(@PathVariable Long id) {
        rateioService.deletarRateio(id);
        return ResponseEntity.noContent().build();
    }
}
