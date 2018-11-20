package com.uem.boxit.controller;

import com.uem.boxit.dto.*;
import com.uem.boxit.exception.ObjectNotFoundException;
import com.uem.boxit.model.Produto;
import com.uem.boxit.service.ProdutoService;
import com.uem.boxit.storage.S3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping(
        value = "/produto",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
public class ProdutoController {

    @Autowired
    private S3 s3;

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public Page<Produto> listAll(Pageable pageable) {
        return produtoService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> getOne(@PathVariable Integer id) {
        Optional<Produto> p = produtoService.getOne(id);
        return p.isPresent() ? ResponseEntity.ok(p.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping("/filtrar")
    public Page<Produto> filtrar(Pageable pageable, @RequestParam(required = false) String nome, @RequestParam(required = false) String categoria, @RequestParam(required = true) Integer enable) {
        Page<Produto> page = produtoService.filtrar(nome, categoria, enable, pageable);
        if (page != null)
            return page;
        else
            throw new ObjectNotFoundException("Os argumentos não devem ser nulos");
    }

    @PostMapping
    public ResponseEntity<Produto> create(@RequestBody NewProdutoDTO dto) {
        Produto p = produtoService.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
                .buildAndExpand(p.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/nome")
    public ResponseEntity<Boolean> nomeExist(@RequestBody NomeDTO dto) {
        Boolean exist = produtoService.nomeExist(dto.getNome());
        return ResponseEntity.ok(exist);
    }

    @PostMapping("/sku")
    public ResponseEntity<Boolean> skuExist(@RequestBody SkuDTO dto) {
        Boolean exist = produtoService.skuExist(dto.getSku());
        return ResponseEntity.ok(exist);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> update(@PathVariable Integer id, @RequestBody Produto p) {
        Produto prod = produtoService.atualizar(id, p);
        return ResponseEntity.ok(prod);
    }

    @PutMapping("/{id}/quantidade")
    public ResponseEntity<Produto> atualizarQtd(@PathVariable Integer id, @RequestBody UpdateQtdDTO dto) {
        Produto prod = produtoService.atualizarQuantidade(id, dto);
        return ResponseEntity.ok(prod);
    }

    @PutMapping("/{id}/enable")
    public ResponseEntity atualizarEnable(@PathVariable Integer id, @RequestBody EnableDTO dto) {
        produtoService.atualizarEnable(id, dto.getEnabled());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/foto")
    public FotoDTO uploadFoto(@RequestParam MultipartFile foto) throws IOException {
        String nome = s3.salvarTemporariamente(foto);
        return new FotoDTO(nome, s3.configurarUrl(nome));
    }
}
