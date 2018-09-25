package com.uem.boxit.repository;

import com.uem.boxit.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Optional<Cliente> findByCnpj(String cnpj);
    Optional<Cliente> findByEmail(String email);
    Optional<Cliente> findByConfirmCode(String code);
    Optional<Cliente> findByPasswordResetCode(String code);
    List<Cliente> findByPasswordRestCodeExpiryDateLessThanEqual(Date date);

    Page<Cliente> findByEnableIsTrue(Pageable pageable);
    Page<Cliente> findByCnpjContainsAndEnableIsTrue(String cnpj, Pageable pageable);
    Page<Cliente> findByNomeContainsAndEnableIsTrue(String nome, Pageable pageable);
    Page<Cliente> findByNomeFantasiaContainsAndEnableIsTrue(String nomeFantasia, Pageable pageable);
    Page<Cliente> findByCnpjContainsAndEnableIsTrueAndNomeContains(String cnpj, String nome,Pageable pageable);
    Page<Cliente> findByCnpjContainsAndEnableIsTrueAndNomeFantasiaContains(String cnpj, String nomeFantasia,Pageable pageable);
    Page<Cliente> findByNomeContainsAndEnableIsTrueAndNomeFantasiaContains(String nome, String nomeFantasia,Pageable pageable);
    Page<Cliente> findByNomeFantasiaContainsAndNomeContainsAndCnpjContainsAndEnableIsTrue(String nomeFantasia, String nome, String cnpj, Pageable pageable);


    Page<Cliente> findByEnableIsFalse(Pageable pageable);
    Page<Cliente> findByCnpjContainsAndEnableIsFalse(String cnpj, Pageable pageable);
    Page<Cliente> findByNomeContainsAndEnableIsFalse(String nome, Pageable pageable);
    Page<Cliente> findByNomeFantasiaContainsAndEnableIsFalse(String nomeFantasia, Pageable pageable);
    Page<Cliente> findByCnpjContainsAndEnableIsFalseAndNomeContains(String cnpj, String nome,Pageable pageable);
    Page<Cliente> findByCnpjContainsAndEnableIsFalseAndNomeFantasiaContains(String cnpj, String nomeFantasia,Pageable pageable);
    Page<Cliente> findByNomeContainsAndEnableIsFalseAndNomeFantasiaContains(String nome, String nomeFantasia,Pageable pageable);
    Page<Cliente> findByNomeFantasiaContainsAndNomeContainsAndCnpjContainsAndEnableIsFalse(String nomeFantasia, String nome, String cnpj, Pageable pageable);


    Page<Cliente> findByCnpjContains(String cnpj, Pageable pageable);
    Page<Cliente> findByNomeContains(String nome, Pageable pageable);
    Page<Cliente> findByNomeFantasiaContains(String nomeFantasia, Pageable pageable);
    Page<Cliente> findByCnpjContainsAndNomeContains(String cnpj, String nome,Pageable pageable);
    Page<Cliente> findByCnpjContainsAndNomeFantasiaContains(String cnpj, String nomeFantasia,Pageable pageable);
    Page<Cliente> findByNomeContainsAndNomeFantasiaContains(String nome, String nomeFantasia,Pageable pageable);
    Page<Cliente> findByNomeFantasiaContainsAndNomeContainsAndCnpjContains(String nomeFantasia, String nome, String cnpj, Pageable pageable);

}
