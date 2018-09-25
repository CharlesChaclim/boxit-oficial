package com.uem.boxit.controller;

import com.uem.boxit.dto.EnableDTO;
import com.uem.boxit.dto.NewCategoriaDTO;
import com.uem.boxit.dto.NomeDTO;
import com.uem.boxit.dto.UpdateCategoriaDTO;
import com.uem.boxit.model.Categoria;
import com.uem.boxit.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping(
        value = "/categoria",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public Page<Categoria> listAll(Pageable pageable) {
        return categoriaService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> getOne(@PathVariable Integer id) {
        Optional<Categoria> c = categoriaService.getOne(id);
        return c.isPresent() ? ResponseEntity.ok(c.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Categoria> create(@RequestBody NewCategoriaDTO dto) {
        Categoria c = categoriaService.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
                .buildAndExpand(c.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/nome")
    public ResponseEntity<Boolean> nomeExist(@RequestBody NomeDTO dto) {
        Boolean exist = categoriaService.nomeExist(dto.getNome());
        return ResponseEntity.ok(exist);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> update(@PathVariable Integer id, @RequestBody UpdateCategoriaDTO c) {
        Categoria cat = categoriaService.update(id, c);
        return ResponseEntity.ok(cat);
    }

    @PutMapping("/{id}/enable")
    public ResponseEntity atualizarEnable(@PathVariable Integer id, @RequestBody EnableDTO dto) {
        categoriaService.atualizarEnable(id, dto.getEnabled());
        return ResponseEntity.ok().build();
    }
}
