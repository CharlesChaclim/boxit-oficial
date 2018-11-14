package com.uem.boxit.controller;

import com.uem.boxit.model.ItemPedido;
import com.uem.boxit.model.Pedido;
import com.uem.boxit.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(
        value = "/pedido",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/pedido")
    public Page<ItemPedido> listAll(Pageable pageable) {
        return pedidoService.getAll(pageable);
    }

    @GetMapping
    public Page<Pedido> listCnpj(Pageable pageable, String cnpj) {
        return pedidoService.getCnpj(pageable, cnpj);
    }


}
