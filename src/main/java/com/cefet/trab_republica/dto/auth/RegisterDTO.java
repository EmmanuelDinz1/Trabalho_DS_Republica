package com.cefet.trab_republica.dto.auth;

import com.cefet.trab_republica.entities.UserRole;
import java.time.LocalDate;

public record RegisterDTO(String nome, String cpf, LocalDate dataNascimento, String celular, String email, String contatosFamilia, String senha, UserRole role) {
}
