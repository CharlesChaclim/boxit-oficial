package com.uem.boxit.controller;

import com.uem.boxit.dto.UpdatePagamentoDTO;
import com.uem.boxit.model.Pagamento;
import com.uem.boxit.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(
        value = "/pagamento",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @GetMapping("/{boleto}")
    public ResponseEntity<Pagamento> getboleto(@PathVariable String boleto) {
        Optional<Pagamento> p = pagamentoService.getBoleto(boleto);
        return p.isPresent() ? ResponseEntity.ok(p.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public Page<Pagamento> listAll(Pageable pageable) {
        return pagamentoService.getAll(pageable);
    }

    @PutMapping()
    public ResponseEntity<Pagamento> update(@RequestBody UpdatePagamentoDTO pagamento) {
        Pagamento pag = pagamentoService.update(pagamento);
        return ResponseEntity.ok(pag);
    }
}
