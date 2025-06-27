package com.cefet.trab_republica.services;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cefet.trab_republica.dto.GastosTipoDTO;
import com.cefet.trab_republica.entities.Conta;
import com.cefet.trab_republica.entities.HistoricoConta;
import com.cefet.trab_republica.entities.Rateio;
import com.cefet.trab_republica.entities.SituacaoConta;
import com.cefet.trab_republica.entities.StatusRateio;
import com.cefet.trab_republica.repositories.ContaRepository;
import com.cefet.trab_republica.repositories.HistoricoContaRepository;
import com.cefet.trab_republica.repositories.RateioRepository;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private RateioRepository rateioRepository;

    @Autowired
    private HistoricoContaRepository historicoRepository;

    public Conta criarConta(Conta conta) {
        conta.setSituacao(SituacaoConta.PENDENTE);
        Conta salva = contaRepository.save(conta);

        // Registra rateios vinculando à conta recém-criada
        if (salva.getRateios() != null) {
            for (Rateio r : salva.getRateios()) {
                r.setConta(salva);
                r.setStatus(StatusRateio.PENDENTE);
                rateioRepository.save(r);
            }
        }

        // Adiciona histórico de criação
        HistoricoConta hist = new HistoricoConta();
        hist.setConta(salva);
        hist.setMorador(salva.getResponsavel());
        hist.setAcao(SituacaoConta.PENDENTE);
        hist.setTimestamp(Instant.now());
        historicoRepository.save(hist);

        return salva;
    }

    public List<Conta> listarContas() {
        return contaRepository.findAll();
    }

    public Conta buscarConta(Long id) {
        return contaRepository.findById(id).orElse(null);
    }
    
 // RF-007
    public List<Conta> extrato(LocalDate inicio, LocalDate fim) {
        return contaRepository.findByDataVencimentoBetween(inicio, fim);
    }

    // RF-008a
    public List<Conta> listarContasEmAberto() {
        return contaRepository.findContasEmAberto();
    }

    // RF-008b
    public List<GastosTipoDTO> gastosPorTipo() {
        return contaRepository.totalGastosPorTipo();
    }

    public Conta atualizarConta(Long id, Conta dadosAtualizados) {
        Conta existente = contaRepository.findById(id).orElse(null);
        if (existente == null) {
            return null;
        }

        // Impede edição se já quitada ou cancelada
        if (existente.getSituacao() == SituacaoConta.QUITADA ||
            existente.getSituacao() == SituacaoConta.CANCELADA) {
            throw new RuntimeException("Não é possível editar conta quitada ou cancelada.");
        }

        // Atualiza campos
        existente.setDataVencimento(dadosAtualizados.getDataVencimento());
        existente.setValor(dadosAtualizados.getValor());
        existente.setTipoConta(dadosAtualizados.getTipoConta());
        existente.setResponsavel(dadosAtualizados.getResponsavel());
        existente.setObservacao(dadosAtualizados.getObservacao());

        // Atualiza rateios (exemplo simples: remove antigos e recria)
        rateioRepository.deleteAll(existente.getRateios());
        existente.getRateios().clear();
        if (dadosAtualizados.getRateios() != null) {
            for (Rateio r : dadosAtualizados.getRateios()) {
                r.setConta(existente);
                r.setStatus(StatusRateio.PENDENTE);
                rateioRepository.save(r);
                existente.getRateios().add(r);
            }
        }

        // Salva conta atualizada
        Conta atualizada = contaRepository.save(existente);

        // Adiciona histórico de atualização
        HistoricoConta hist = new HistoricoConta();
        hist.setConta(atualizada);
        hist.setMorador(atualizada.getResponsavel());
        hist.setAcao(SituacaoConta.PENDENTE);
        hist.setTimestamp(Instant.now());
        historicoRepository.save(hist);

        return atualizada;
    }

    public void deletarConta(Long id) {
        Conta existente = contaRepository.findById(id).orElse(null);
        if (existente != null) {
            contaRepository.deleteById(id);

            HistoricoConta hist = new HistoricoConta();
            hist.setConta(existente);
            hist.setMorador(existente.getResponsavel());
            hist.setAcao(SituacaoConta.CANCELADA);
            hist.setTimestamp(Instant.now());
            historicoRepository.save(hist);
        }
    }

    // Replicação de conta: cria uma nova cópia com alterações permitidas
    public Conta replicarConta(Long id, Conta dadosAlterados) {
        Conta original = contaRepository.findById(id).orElse(null);
        if (original == null) {
            return null;
        }

        // Cria nova conta baseada na original
        Conta copia = new Conta();
        copia.setTipoConta(original.getTipoConta());
        copia.setObservacao(original.getObservacao());
        copia.setDataVencimento(dadosAlterados.getDataVencimento());
        copia.setValor(dadosAlterados.getValor());
        copia.setResponsavel(dadosAlterados.getResponsavel());
        copia.setSituacao(SituacaoConta.PENDENTE);

        Conta salva = contaRepository.save(copia);

        // Replicar rateios da original
        if (original.getRateios() != null) {
            for (Rateio rOld : original.getRateios()) {
                Rateio novoRateio = new Rateio();
                novoRateio.setConta(salva);
                novoRateio.setMorador(rOld.getMorador());
                novoRateio.setValor(rOld.getValor());
                novoRateio.setStatus(rOld.getStatus());
                rateioRepository.save(novoRateio);
            }
        }

        // Adiciona histórico de replicação
        HistoricoConta hist = new HistoricoConta();
        hist.setConta(salva);
        hist.setMorador(salva.getResponsavel());
        hist.setAcao(SituacaoConta.PENDENTE);
        hist.setTimestamp(Instant.now());
        historicoRepository.save(hist);

        return salva;
    }
}