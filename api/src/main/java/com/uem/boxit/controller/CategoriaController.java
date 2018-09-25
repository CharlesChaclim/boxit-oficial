package com.uem.boxit.controller;

import com.uem.boxit.dto.NewCategoriaDTO;
import com.uem.boxit.model.Categoria;
import com.uem.boxit.service.CategoriaService;
import com.uem.boxit.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
        return p.isPresent() ? ResponseEntity.ok(p.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Categoria> create(@RequestBody NewCategoriaDTO dto) {
        Categoria c = categoriaService.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
                .buildAndExpand(p.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> update(@PathVariable Integer id, @RequestBody Categoria c) {
        Categoria c = categoriaService.update(id, c);
        return ResponseEntity.ok(func);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable Integer id) {
        categoriaService.deletar(id);
        return ResponseEntity.ok().build();
    }
}
