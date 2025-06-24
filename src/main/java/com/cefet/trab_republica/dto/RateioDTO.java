package com.cefet.trab_republica.dto;

public class RateioDTO {
    private Long id;
    private Long contaId;
    private Long moradorId;
    private String status;

    public RateioDTO() {}

    public RateioDTO(Long id, Long contaId, Long moradorId, String status) {
        this.id = id;
        this.contaId = contaId;
        this.moradorId = moradorId;
        this.status = status;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}

