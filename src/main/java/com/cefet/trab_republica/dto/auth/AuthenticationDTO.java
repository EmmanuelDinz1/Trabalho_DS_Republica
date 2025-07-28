package com.cefet.trab_republica.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthenticationDTO(
    @NotBlank @Email String email,
    @NotBlank String senha
) {}
