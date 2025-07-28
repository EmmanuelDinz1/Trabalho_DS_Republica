package com.cefet.trab_republica.repositories;

import com.cefet.trab_republica.entities.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
    void deleteByMoradorId(Long moradorId);
}
