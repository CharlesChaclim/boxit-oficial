package com.uem.boxit.repository;

import com.uem.boxit.model.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    Page<Pedido> findByClienteId(Pageable pageable, Integer id);
}
