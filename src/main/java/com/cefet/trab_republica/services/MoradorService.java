package com.cefet.trab_republica.services;

import com.cefet.trab_republica.dto.SaldoMoradorDTO;
import com.cefet.trab_republica.entities.Morador;
import com.cefet.trab_republica.repositories.MoradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Service
public class MoradorService {

    @Autowired
    private MoradorRepository moradorRepository;

    // --- MÉTODOS DE NEGÓCIO ---

    public Morador criarMorador(Morador morador) {
        // A senha já deve vir codificada do controller
        return moradorRepository.save(morador);
    }

    public UserDetails findByEmail(String email) {
        return moradorRepository.findByEmail(email);
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
        return moradorRepository.findById(id).map(existente -> {
            existente.setNome(dadosAtualizados.getNome());
            existente.setEmail(dadosAtualizados.getEmail());
            existente.setCpf(dadosAtualizados.getCpf());
            existente.setCelular(dadosAtualizados.getCelular());
            existente.setContatosFamilia(dadosAtualizados.getContatosFamilia());
            existente.setDataNascimento(dadosAtualizados.getDataNascimento());
            // A senha não é atualizada aqui, deve ser um processo separado
            return moradorRepository.save(existente);
        }).orElse(null);
    }

    public void deletarMorador(Long id) {
        moradorRepository.deleteById(id);
    }
}
