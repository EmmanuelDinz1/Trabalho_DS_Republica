package com.cefet.trab_republica.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_conta")
public class Conta implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255)
    private String observacao;

    @Column(nullable = false)
    private Double valor;

    @Column(name = "data_vencimento", nullable = false)
    private LocalDate dataVencimento;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_responsavel")
    private Morador responsavel;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_tipoconta")
    private TipoConta tipoConta;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SituacaoConta situacao;

    @OneToMany(mappedBy = "conta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rateio> rateios = new ArrayList<>();

    // Construtores, Getters, Setters, etc. (sem alterações)
    public Conta() {}

    public Conta(Long id, String observacao, Double valor, LocalDate dataVencimento, Morador responsavel, TipoConta tipoConta, SituacaoConta situacao) {
        this.id = id;
        this.observacao = observacao;
        this.valor = valor;
        this.dataVencimento = dataVencimento;
        this.responsavel = responsavel;
        this.tipoConta = tipoConta;
        this.situacao = situacao;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }
    public LocalDate getDataVencimento() { return dataVencimento; }
    public void setDataVencimento(LocalDate dataVencimento) { this.dataVencimento = dataVencimento; }
    public Morador getResponsavel() { return responsavel; }
    public void setResponsavel(Morador responsavel) { this.responsavel = responsavel; }
    public TipoConta getTipoConta() { return tipoConta; }
    public void setTipoConta(TipoConta tipoConta) { this.tipoConta = tipoConta; }
    public SituacaoConta getSituacao() { return situacao; }
    public void setSituacao(SituacaoConta situacao) { this.situacao = situacao; }
    public List<Rateio> getRateios() { return rateios; }
    public void setRateios(List<Rateio> rateios) { this.rateios = rateios; }

    @Override
    public int hashCode() { return Objects.hash(id); }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Conta)) return false;
        return Objects.equals(id, ((Conta)o).id);
    }
}
