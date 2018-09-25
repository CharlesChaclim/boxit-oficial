package com.uem.boxit.repository;

import com.uem.boxit.model.Funcionario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
    Optional<Funcionario> findByCpf(String cpf);

    Page<Funcionario> findByNomeContainsAndCpfContainsAndEmailContains(String nome, String cpf, String email, Pageable pageable);
    Page<Funcionario> findByCpfContainsAndEmailContains(String cpf, String email, Pageable pageable);
    Page<Funcionario> findByNomeContainsAndEmailContains(String nome, String email, Pageable pageable);
    Page<Funcionario> findByNomeContainsAndCpfContains(String nome, String cpf, Pageable pageable);
    Page<Funcionario> findByNomeContains(String nome, Pageable pageable);
    Page<Funcionario> findByCpfContains(String cpf, Pageable pageable);
    Page<Funcionario> findByEmailContains(String email, Pageable pageable);
}
