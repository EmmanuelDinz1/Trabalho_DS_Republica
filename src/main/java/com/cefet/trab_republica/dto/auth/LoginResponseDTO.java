package com.cefet.trab_republica.dto.auth;

public class LoginResponseDTO {
    private String token;
    private String type = "Bearer";
    private Long moradorId;
    private String moradorNome;

    public LoginResponseDTO(String token, Long moradorId, String moradorNome) {
        this.token      = token;
        this.moradorId  = moradorId;
        this.moradorNome= moradorNome;
    }

    public String getToken()       { return token; }
    public String getType()        { return type; }
    public Long   getMoradorId()   { return moradorId; }
    public String getMoradorNome() { return moradorNome; }
}
