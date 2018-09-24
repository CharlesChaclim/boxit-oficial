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
    Page<Cliente> findByEnableIsTrue(Pageable pageable);
    Optional<Cliente> findByEmail(String email);
    Optional<Cliente> findByConfirmCode(String code);
    Optional<Cliente> findByPasswordResetCode(String code);
    List<Cliente> findByPasswordRestCodeExpiryDateLessThanEqual(Date date);
}
