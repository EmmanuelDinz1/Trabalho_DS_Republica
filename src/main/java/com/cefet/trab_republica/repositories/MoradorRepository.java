package com.cefet.trab_republica.repositories;

import java.util.List;                              
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.cefet.trab_republica.entities.Morador;
import com.cefet.trab_republica.dto.SaldoMoradorDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MoradorRepository extends JpaRepository<Morador, Long> {

    @Query("""
        SELECT m.id        AS moradorId,
               m.nome      AS nome,
               SUM(r.valor) AS saldoDevedor
          FROM Rateio r
          JOIN r.morador m
         WHERE r.status = 'EM_ABERTO'
         GROUP BY m.id, m.nome
    """)
    List<SaldoMoradorDTO> calcularSaldoMoradores();

    UserDetails findByEmail(String email);
    
    Optional<Morador> findMoradorByEmail(String email);
}
