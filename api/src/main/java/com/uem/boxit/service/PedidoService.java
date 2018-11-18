package com.uem.boxit.service;
import com.uem.boxit.model.Cliente;
import com.uem.boxit.model.ItemPedido;
import com.uem.boxit.model.Pedido;
import com.uem.boxit.repository.ClienteRepository;
import com.uem.boxit.repository.ItemPedidoRepository;
import com.uem.boxit.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    public Page<ItemPedido> getAll(Pageable pageable) {
        return itemPedidoRepository.findAll(pageable);
    }

    public Page<Pedido> getCnpj(Pageable pageable, String cnpj) {
        Cliente cli = clienteRepository.findByCnpj(cnpj).get();
        return pedidoRepository.findByClienteId(pageable,cli.getId());
    }


}
