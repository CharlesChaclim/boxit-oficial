package com.uem.boxit.repository;

import com.uem.boxit.model.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Integer> {
    Optional<Carrinho> findById(Integer id);
}
