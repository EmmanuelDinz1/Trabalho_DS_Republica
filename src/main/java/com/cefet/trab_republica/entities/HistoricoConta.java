package com.cefet.trab_republica.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "tb_historico_conta")
public class HistoricoConta implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "conta_id")
    private Conta conta;

    @ManyToOne(optional = false)
    @JoinColumn(name = "morador_id")
    private Morador morador;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SituacaoConta acao;

    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;

    public HistoricoConta() {}
    
    // Getters / Setters
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public Morador getMorador() {
        return morador;
    }

    public void setMorador(Morador morador) {
        this.morador = morador;
    }

    public SituacaoConta getAcao() {
        return acao;
    }

    public void setAcao(SituacaoConta acao) {
        this.acao = acao;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
