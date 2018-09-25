package com.uem.boxit.service;

import com.uem.boxit.dto.NewCategoriaDTO;
import com.uem.boxit.dto.UpdateCategoriaDTO;
import com.uem.boxit.model.Categoria;
import com.uem.boxit.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.function.Function;

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

    public Categoria salvar(NewCategoriaDTO dto) {
        Categoria c = toCategoria.apply(dto);
        return categoriaRepository.save(c);
    }

    public Boolean nomeExist(String nome) {
        return categoriaRepository.findByNome(nome).isPresent();
    }

    @Transactional
    public Categoria update(Integer id, UpdateCategoriaDTO categoria) {
        Categoria c = fromUpdateDTO.apply(categoria);
        return categoriaRepository.save(c);
    }

    private Function<UpdateCategoriaDTO, Categoria> fromUpdateDTO = dto -> {
        Categoria c = categoriaRepository.getOne(dto.getId());
        c.setNome(dto.getNome());
        return c;
    };

    private Function<NewCategoriaDTO, Categoria> toCategoria = dto -> {
        Categoria c = new Categoria();
        c.setNome(dto.getNome());
        c.setEnable(true);
        return c;
    };

    @Transactional
    public void atualizarEnable(Integer id, Boolean enable){
        Categoria categoria = categoriaRepository.getOne(id);
        if(!enable)
            categoria.setEnable(true);
        else
            categoria.setEnable(false);
        categoriaRepository.save(categoria);
    }
}
