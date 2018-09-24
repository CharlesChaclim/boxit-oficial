package com.uem.boxit.repository;

import com.uem.boxit.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    Optional<Produto> findBySku(String sku);
}
