package com.uem.boxit.service;

import com.uem.boxit.dto.NewProdutoDTO;
import com.uem.boxit.model.Produto;
import com.uem.boxit.repository.CategoriaRepository;
import com.uem.boxit.repository.ProdutoRepository;
import com.uem.boxit.storage.S3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private S3 s3;

    private Produto salvar(NewProdutoDTO dto) {
        Produto p = toProduto.apply(dto);
        return produtoRepository.save(p);
    }

    private Function<NewProdutoDTO, Produto> toProduto = dto -> {
        Produto p = new Produto();
        p.setCategoria(categoriaRepository.getOne(dto.getCategoriaId()));
        p.setNome(dto.getNome());
        p.setPreco(dto.getPreco());
        p.setDescricao(dto.getDescricao());
        p.setUnidadeLote(dto.getUnidadeLote());
        p.setQtd(dto.getQtd());
        p.setSku(dto.getSku());
        Map<String, String> fotos = new HashMap<>();
        dto.getFotos().forEach(k -> fotos.put(k, s3.configurarUrl(k)));
        p.setFotos(fotos);
        return p;
    };
}
