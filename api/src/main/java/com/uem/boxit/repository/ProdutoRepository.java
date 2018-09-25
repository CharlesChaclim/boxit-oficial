package com.uem.boxit.repository;

import com.uem.boxit.model.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    Optional<Produto> findByNome(String nome);
    Optional<Produto> findBySku(String sku);

    Page<Produto> findByEnableIsTrue(Pageable pageable);
    Page<Produto> findByNomeContainsAndEnableIsTrue(String nome, Pageable pageable);
    Page<Produto> findByCategoria_NomeContainsAndEnableIsTrue(String categoria, Pageable pageable);
    Page<Produto> findByNomeContainsAndCategoria_NomeContainsAndEnableIsTrue(String nome, String categoria, Pageable pageable);

    Page<Produto> findByEnableIsFalse(Pageable pageable);
    Page<Produto> findByNomeContainsAndEnableIsFalse(String nome, Pageable pageable);
    Page<Produto> findByCategoria_NomeContainsAndEnableIsFalse(String categoria, Pageable pageable);
    Page<Produto> findByNomeContainsAndCategoria_NomeContainsAndEnableIsFalse(String nome, String categoria, Pageable pageable);

    Page<Produto> findByNomeContains(String nome, Pageable pageable);
    Page<Produto> findByCategoria_NomeContains(String categoria, Pageable pageable);
    Page<Produto> findByNomeContainsAndCategoria_NomeContains(String nome, String categoria, Pageable pageable);
}
