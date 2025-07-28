package com.cefet.trab_republica.dto;

import jakarta.validation.constraints.NotBlank;

public record ResetPasswordDTO(
    @NotBlank String token,
    @NotBlank String novaSenha
) {}