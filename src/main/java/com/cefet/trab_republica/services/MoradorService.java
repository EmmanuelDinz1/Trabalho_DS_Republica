package com.cefet.trab_republica.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cefet.trab_republica.dto.SaldoMoradorDTO;
import com.cefet.trab_republica.entities.Morador;
import com.cefet.trab_republica.repositories.MoradorRepository;
	
@Service
// Implementa a interface para integrar com o Spring Security
public class MoradorService implements UserDetailsService {

    @Autowired
    private MoradorRepository moradorRepository;
    
    // Injeta o mesmo PasswordEncoder definido no SecurityConfig
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Este método é OBRIGATÓRIO pela interface UserDetailsService.
    // O Spring Security o chamará automaticamente durante a autenticação.
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Morador morador = moradorRepository.findByEmail(email);
        if (morador == null) {
            throw new UsernameNotFoundException("Usuário não encontrado com o email: " + email);
        }
        // Retorna um objeto UserDetails que o Spring Security entende
        return new User(morador.getEmail(), morador.getSenha(), new ArrayList<>());
    }

    // Seu método de autenticação agora está mais simples
    public Morador autenticarEObterMorador(String email, String senha) {
        Morador morador = moradorRepository.findByEmail(email);
        if (morador != null && passwordEncoder.matches(senha, morador.getSenha())) {
            return morador;
        }
        return null;
    }

    public Morador cadastrarMorador(Morador morador) {
        // A senha já deve vir codificada do controller
        return moradorRepository.save(morador);
    }
    
    public Morador findByEmail(String email) {
        return moradorRepository.findByEmail(email);
    }
    
    // --- DEMAIS MÉTODOS (sem alterações) ---

    public List<Morador> listarMoradores() {
        return moradorRepository.findAll();
    }
    
    public List<SaldoMoradorDTO> getSaldos() {
        return moradorRepository.calcularSaldoMoradores();
    }

    public Morador buscarMorador(Long id) {
        return moradorRepository.findById(id).orElse(null);
    }

    public Morador atualizarMorador(Long id, Morador dadosAtualizados) {
        Morador existente = moradorRepository.findById(id).orElse(null);
        if (existente == null) {
            return null;
        }
        existente.setNome(dadosAtualizados.getNome());
        existente.setEmail(dadosAtualizados.getEmail());
        if (dadosAtualizados.getSenha() != null && !dadosAtualizados.getSenha().isEmpty()) {
            String senhaHash = passwordEncoder.encode(dadosAtualizados.getSenha());
            existente.setSenha(senhaHash);
        }
        return moradorRepository.save(existente);
    }

    public void deletarMorador(Long id) {
        moradorRepository.deleteById(id);
    }
    
    public void recuperarSenha(String email) {
        // TODO: implementar envio de token ou nova senha
    }
}