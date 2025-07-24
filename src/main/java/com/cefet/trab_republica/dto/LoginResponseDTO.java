package com.cefet.trab_republica.dto;

public class LoginResponseDTO {
    private String status; 
    private Long moradorId;
    private String moradorNome;
    // No futuro, aqui estaria o token JWT

    public LoginResponseDTO(String status, Long moradorId, String moradorNome) {
        this.status = status;
        this.moradorId = moradorId;
        this.moradorNome = moradorNome;
    }

    // Getters e Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getMoradorId() {
        return moradorId;
    }

    public void setMoradorId(Long moradorId) {
        this.moradorId = moradorId;
    }

    public String getMoradorNome() {
        return moradorNome;
    }

    public void setMoradorNome(String moradorNome) {
        this.moradorNome = moradorNome;
    }
}