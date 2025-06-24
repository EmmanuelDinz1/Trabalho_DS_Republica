package com.cefet.trab_republica.dto;

import java.time.Instant;

public class HistoricoContaDTO {
    private Long id;
    private Long contaId;
    private Long moradorId;
    private String acao;
    private Instant timestamp;

    public HistoricoContaDTO() {}

    public HistoricoContaDTO(Long id, Long contaId, Long moradorId,
                             String acao, Instant timestamp) {
        this.id = id;
        this.contaId = contaId;
        this.moradorId = moradorId;
        this.acao = acao;
        this.timestamp = timestamp;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getContaId() {
		return contaId;
	}

	public void setContaId(Long contaId) {
		this.contaId = contaId;
	}

	public Long getMoradorId() {
		return moradorId;
	}

	public void setMoradorId(Long moradorId) {
		this.moradorId = moradorId;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}
}


