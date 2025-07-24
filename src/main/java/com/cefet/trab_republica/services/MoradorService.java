package com.cefet.trab_republica.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder; // Importação correta

import com.cefet.trab_republica.dto.SaldoMoradorDTO;
import com.cefet.trab_republica.entities.Morador;
import com.cefet.trab_republica.repositories.MoradorRepository;
	
@Service
public class MoradorService {

    @Autowired
    private MoradorRepository moradorRepository;

    // --- MELHORIA: Injetando o PasswordEncoder do Spring ---
    // Em vez de "new BCryptPasswordEncoder()", usamos a instância gerenciada pelo Spring.
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Morador cadastrarMorador(Morador morador) {
        // A lógica de codificar a senha no cadastro continua correta
        String senhaHash = passwordEncoder.encode(morador.getSenha());
        morador.setSenha(senhaHash);
        return moradorRepository.save(morador);
    }

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
        
        // Lógica aprimorada para só atualizar a senha se uma nova for fornecida
        if (dadosAtualizados.getSenha() != null && !dadosAtualizados.getSenha().isEmpty()) {
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

    // --- NOVO MÉTODO IMPLEMENTADO ---
    // Autentica as credenciais e retorna o objeto Morador completo em caso de sucesso.
    public Morador autenticarEObterMorador(String email, String senha) {
        Morador morador = moradorRepository.findByEmail(email);

        // 1. Verifica se o morador existe
        if (morador == null) {
            return null; 
        }

        // 2. Compara a senha da requisição com o hash salvo no banco
        if (passwordEncoder.matches(senha, morador.getSenha())) {
            return morador; // Sucesso! Retorna o objeto do morador.
        }

        return null; // Senha inválida
    }
    
    // O método antigo 'autenticar' pode ser removido ou mantido por compatibilidade.
    // Se mantido, ele deve usar a nova lógica.
    public boolean autenticar(String email, String senha) {
        return autenticarEObterMorador(email, senha) != null;
    }

    // Stub para recuperação de senha
    public void recuperarSenha(String email) {
        // TODO: implementar envio de token ou nova senha
        System.out.println("Solicitação de recuperação de senha para: " + email);
    }
}