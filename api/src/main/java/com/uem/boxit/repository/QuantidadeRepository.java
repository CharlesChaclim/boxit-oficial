package com.uem.boxit.repository;

import com.uem.boxit.model.Quantidade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuantidadeRepository extends JpaRepository<Quantidade, Integer> {
}
