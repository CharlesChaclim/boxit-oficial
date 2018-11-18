package com.uem.boxit.repository;

import com.uem.boxit.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {
    Optional<Pagamento> findByNrBoleto(String nrBoleto);
    Pagamento getByNrBoleto(String nrBoleto);
}
