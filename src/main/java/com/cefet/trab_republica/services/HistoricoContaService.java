package com.cefet.trab_republica.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cefet.trab_republica.entities.HistoricoConta;
import com.cefet.trab_republica.repositories.HistoricoContaRepository;

@Service
public class HistoricoContaService {

    @Autowired
    private HistoricoContaRepository historicoRepository;

    public List<HistoricoConta> listarHistoricos() {
        return historicoRepository.findAll();
    }

    public List<HistoricoConta> buscarHistoricoPorConta(Long contaId) {
        return historicoRepository.findByContaId(contaId);
    }

    public HistoricoConta criarHistorico(HistoricoConta historico) {
        return historicoRepository.save(historico);
    }
}