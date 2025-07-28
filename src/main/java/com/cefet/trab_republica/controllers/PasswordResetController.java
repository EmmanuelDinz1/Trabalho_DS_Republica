package com.cefet.trab_republica.controllers;

import com.cefet.trab_republica.dto.ForgotPasswordDTO;
import com.cefet.trab_republica.dto.ResetPasswordDTO;
import com.cefet.trab_republica.services.PasswordResetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/password")
public class PasswordResetController {

    @Autowired
    private PasswordResetService resetService;

    @PostMapping("/forgot")
    public ResponseEntity<?> forgotPassword(@RequestBody @Valid ForgotPasswordDTO dto) {
        String token = resetService.createTokenFor(dto.email());
        // TODO: enviar token por e‑mail
        return ResponseEntity.ok("Token gerado: " + token);
    }

    @PostMapping("/reset")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid ResetPasswordDTO dto) {
        resetService.resetPassword(dto.token(), dto.novaSenha());
        return ResponseEntity.ok("Senha resetada com sucesso");
    }
}
