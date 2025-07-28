package com.cefet.trab_republica.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tb_rateio")
public class Rateio implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double valor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusRateio status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_conta")
    private Conta conta;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_morador")
    private Morador morador;

    // Construtores, Getters, Setters, etc...
    public Rateio() {}

    public Rateio(Long id, Double valor, StatusRateio status, Conta conta, Morador morador) {
        this.id = id;
        this.valor = valor;
        this.status = status;
        this.conta = conta;
        this.morador = morador;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }
    public StatusRateio getStatus() { return status; }
    public void setStatus(StatusRateio status) { this.status = status; }
    public Conta getConta() { return conta; }
    public void setConta(Conta conta) { this.conta = conta; }
    public Morador getMorador() { return morador; }
    public void setMorador(Morador morador) { this.morador = morador; }

    @Override
    public int hashCode() { return Objects.hash(id); }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rateio)) return false;
        return Objects.equals(id, ((Rateio)o).id);
    }
}
