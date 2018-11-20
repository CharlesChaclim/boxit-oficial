package com.uem.boxit.controller;

import com.uem.boxit.dto.NewCarrinhoDTO;
import com.uem.boxit.model.Carrinho;
import com.uem.boxit.model.ItemPedido;
import com.uem.boxit.service.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(
        value = "/carrinho",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
public class CarrinhoController {
    @Autowired
    private CarrinhoService carrinhoService;

    @GetMapping("/{id}")
    public ResponseEntity<Carrinho> get(@PathVariable Integer id) {
        Optional<Carrinho> c = carrinhoService.getOne(id);
        return c.isPresent() ? ResponseEntity.ok(c.get()) : createEmpty();
    }

    @PostMapping
    public ResponseEntity<Carrinho> add(@RequestBody NewCarrinhoDTO dto) {
        Carrinho c = carrinhoService.add(dto);
        return ResponseEntity.ok(c);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        carrinhoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<Carrinho> createEmpty() {
        Carrinho c = carrinhoService.createEmpty();
        return ResponseEntity.ok(c);
    }
}
