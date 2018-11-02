package com.uem.boxit.repository;

import com.uem.boxit.model.ItemPedido;
import com.uem.boxit.model.Pedido;
import io.swagger.models.auth.In;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer> {
}
