package com.cefet.trab_republica.dto;

public class TipoContaDTO {
    private Long id;
    private String descricao;
    private String observacao;

    public TipoContaDTO() {}

    public TipoContaDTO(Long id, String descricao, String observacao) {
        this.id = id;
        this.descricao = descricao;
        this.observacao = observacao;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
}

