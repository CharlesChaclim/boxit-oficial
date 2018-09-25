package com.uem.boxit.service;

import com.uem.boxit.model.Categoria;
import com.uem.boxit.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    public Page<Categoria> getAll(Pageable pageable) {
        return categoriaRepository.findAll(pageable);
    }

    public Optional<Categoria> getOne(Integer id) {
        return categoriaRepository.findById(id);
    }
}
