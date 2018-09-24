package com.uem.boxit.repository;

import com.uem.boxit.model.Funcionario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
    Optional<Funcionario> findByCpf(String cpf);

    Page<Funcionario> findAllByNomeLikeOrCpfLikeOrEmailLike(String nome, String cpf, String email, Pageable pageable);
    Page<Funcionario> findAllByCpfLikeOrEmailLike(String cpf, String email, Pageable pageable);
    Page<Funcionario> findAllByNomeLikeOrEmailLike(String nome, String email, Pageable pageable);
    Page<Funcionario> findAllByNomeLikeOrCpfLike(String nome, String cpf, Pageable pageable);
    Page<Funcionario> findAllByNomeLike(String nome, Pageable pageable);
    Page<Funcionario> findAllByCpfLike(String cpf, Pageable pageable);
    Page<Funcionario> findAllByEmailLike(String email, Pageable pageable);
}
