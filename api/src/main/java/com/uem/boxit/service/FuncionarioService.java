package com.uem.boxit.service;

import com.uem.boxit.model.Funcionario;
import com.uem.boxit.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public Page<Funcionario> getAll(Pageable pageable) {
        return funcionarioRepository.findAll(pageable);
    }
}
