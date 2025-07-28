package com.cefet.trab_republica.services;

import com.cefet.trab_republica.entities.Morador;
import com.cefet.trab_republica.entities.PasswordResetToken;
import com.cefet.trab_republica.repositories.MoradorRepository;
import com.cefet.trab_republica.repositories.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Optional;

@Service
public class PasswordResetService {

    @Autowired private MoradorRepository moradorRepository;
    @Autowired private PasswordResetTokenRepository tokenRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    private static final long EXPIRATION_MINUTES = 60; // token válido por 60 min

    /** Gera um token, persiste e retorna o token para ser enviado por e‑mail */
    @Transactional
    public String createTokenFor(String email) {
        Morador m = moradorRepository.findByEmail(email);
        if (m == null) throw new IllegalArgumentException("E‑mail não encontrado");

        // remove tokens antigos
        tokenRepository.deleteByMoradorId(m.getId());

        // gera string segura
        byte[] bytes = new byte[24];
        new SecureRandom().nextBytes(bytes);
        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);

        PasswordResetToken prt = new PasswordResetToken();
        prt.setMorador(m);
        prt.setToken(token);
        prt.setCreatedAt(Instant.now());
        prt.setExpiresAt(Instant.now().plus(EXPIRATION_MINUTES, ChronoUnit.MINUTES));
        tokenRepository.save(prt);

        return token;
    }

    /** Valida token e atualiza a senha */
    @Transactional
    public void resetPassword(String token, String novaSenha) {
        PasswordResetToken prt = tokenRepository.findByToken(token)
            .orElseThrow(() -> new IllegalArgumentException("Token inválido"));

        if (prt.getExpiresAt().isBefore(Instant.now())) {
            tokenRepository.delete(prt);
            throw new IllegalArgumentException("Token expirado");
        }

        Morador m = prt.getMorador();
        m.setSenha(passwordEncoder.encode(novaSenha));
        moradorRepository.save(m);

        // invalida o token
        tokenRepository.delete(prt);
    }
}
