package com.cefet.trab_republica.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cefet.trab_republica.entities.Morador;

@Repository
public interface MoradorRepository extends JpaRepository<Morador, Long> {
    // Aqui vamos consultar por email (Achei bastante útil para autenticação)
    Morador findByEmail(String email);
}
