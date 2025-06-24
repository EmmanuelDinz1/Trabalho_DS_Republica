package com.cefet.trab_republica.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.cefet.trab_republica.entities.Morador;
import com.cefet.trab_republica.repositories.MoradorRepository;
	
@Service
public class MoradorService {

    @Autowired
    private MoradorRepository moradorRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Morador cadastrarMorador(Morador morador) {
        // Cria hash da senha antes de salvar
        String senhaHash = passwordEncoder.encode(morador.getSenha());
        morador.setSenha(senhaHash);
        return moradorRepository.save(morador);
    }

    public List<Morador> listarMoradores() {
        return moradorRepository.findAll();
    }

    public Morador buscarMorador(Long id) {
        return moradorRepository.findById(id).orElse(null);
    }

    public Morador atualizarMorador(Long id, Morador dadosAtualizados) {
        Morador existente = moradorRepository.findById(id).orElse(null);
        if (existente == null) {
            return null;
        }
        // Atualiza campos relevantes
        existente.setNome(dadosAtualizados.getNome());
        existente.setEmail(dadosAtualizados.getEmail());
        if (dadosAtualizados.getSenha() != null) {
            String senhaHash = passwordEncoder.encode(dadosAtualizados.getSenha());
            existente.setSenha(senhaHash);
        }
        return moradorRepository.save(existente);
    }

    public void deletarMorador(Long id) {
        moradorRepository.deleteById(id);
    }

    public Morador findByEmail(String email) {
        return moradorRepository.findByEmail(email);
    }

    // Método simplificado de autenticação (login)
    public boolean autenticar(String email, String senha) {
        Morador m = moradorRepository.findByEmail(email);
        if (m == null) {
            return false;
        }
        return passwordEncoder.matches(senha, m.getSenha());
    }

    // Stub para recuperação de senha (detalhes dependem de implementação futura)
    public void recuperarSenha(String email) {
        // TODO: implementar envio de token ou nova senha
    }
}