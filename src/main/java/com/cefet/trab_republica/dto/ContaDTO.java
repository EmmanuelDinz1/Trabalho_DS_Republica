package com.cefet.trab_republica.dto;

import java.time.LocalDate;

public class ContaDTO {
    private Long id;
    private String observacao;
    private Long tipoContaId;
    private double valor;
    private LocalDate dataVencimento;
    private Long responsavelId;
    private String situacao;

    public ContaDTO() {}

    public ContaDTO(Long id, String observacao, Long tipoContaId, double valor,
                    LocalDate dataVencimento, Long responsavelId, String situacao) {
        this.id = id;
        this.observacao = observacao;
        this.tipoContaId = tipoContaId;
        this.valor = valor;
        this.dataVencimento = dataVencimento;
        this.responsavelId = responsavelId;
        this.situacao = situacao;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Long getTipoContaId() {
		return tipoContaId;
	}

	public void setTipoContaId(Long tipoContaId) {
		this.tipoContaId = tipoContaId;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public LocalDate getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public Long getResponsavelId() {
		return responsavelId;
	}

	public void setResponsavelId(Long responsavelId) {
		this.responsavelId = responsavelId;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

}
