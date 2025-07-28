package com.cefet.trab_republica.dto.auth;

import com.cefet.trab_republica.entities.UserRole;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record RegisterDTO(
    @NotBlank String nome,
    @NotBlank String cpf,
    @NotNull LocalDate dataNascimento,
    @NotBlank String celular,
    @NotBlank @Email String email,
    @NotBlank String contatosFamilia,
    @NotBlank String senha,
    @NotNull UserRole role
) {}
