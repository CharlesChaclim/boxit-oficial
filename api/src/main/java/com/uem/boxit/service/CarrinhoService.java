package com.uem.boxit.service;

import com.uem.boxit.dto.NewCarrinhoDTO;
import com.uem.boxit.model.*;
import com.uem.boxit.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class CarrinhoService {

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public Optional<Carrinho> getOne(Integer id) {
        return carrinhoRepository.findById(id);
    }

    public Carrinho createEmpty() {
        Carrinho c = new Carrinho();
        return carrinhoRepository.save(c);
    }

    @Transactional
    public Carrinho add(NewCarrinhoDTO dto) {
        Carrinho cart = carrinhoRepository.getOne(dto.getId());
        List<ItemPedido> itemPedidos = cart.getItems();
        Cliente c = clienteRepository.getOne(dto.getId());
        Pedido p = new Pedido();
        p.setCliente(c);
        p = pedidoRepository.save(p);
        Produto prod = produtoRepository.getOne(dto.getProdutoId());
        ItemPedido item = new ItemPedido(p, prod, dto.getQty(), prod.getPreco(), prod.getDesconto());
        itemPedidoRepository.save(item);
        itemPedidos.add(item);
        cart.setItems(itemPedidos);
        return carrinhoRepository.save(cart);
    }

    public void delete(Integer id) {
        Carrinho c = carrinhoRepository.getOne(id);
        carrinhoRepository.delete(c);
    }

    private Function<NewCarrinhoDTO, Carrinho> fromCarrinhoDTO = dto -> {
        Carrinho cart = carrinhoRepository.getOne(dto.getId());
        List<ItemPedido> items = new ArrayList<>();
        Cliente c = clienteRepository.getOne(dto.getId());
        Pedido p = new Pedido();
        p.setCliente(c);
        p = pedidoRepository.save(p);
        Produto prod = produtoRepository.getOne(dto.getProdutoId());
        ItemPedido itemPedido = new ItemPedido(p, prod, dto.getQty(), prod.getPreco(), 0.0);
        return cart;
    };
}
