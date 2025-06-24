package com.cefet.trab_republica.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cefet.trab_republica.entities.Conta;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {
    List<Conta> findByResponsavelId(Long responsavelId);
}