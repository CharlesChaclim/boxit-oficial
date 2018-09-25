package com.uem.boxit.service;

import com.uem.boxit.dto.NewFuncionarioDTO;
import com.uem.boxit.model.Funcionario;
import com.uem.boxit.model.enums.Role;
import com.uem.boxit.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@Service
public class FuncionarioService {
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private PasswordEncoder encoder;

    public Page<Funcionario> getAll(Pageable pageable) {
        return funcionarioRepository.findAll(pageable);
    }

    public Optional<Funcionario> getOne(Integer id) {
        return funcionarioRepository.findById(id);
    }

    public Page<Funcionario> filter(String nome, String cpf, String email, Pageable pageable) {
        if (!StringUtils.isEmpty(nome) && !StringUtils.isEmpty(cpf) && !StringUtils.isEmpty(email))
            return funcionarioRepository.findByNomeContainsOrCpfContainsOrEmailContains(nome, cpf, email, pageable);
        else if (!StringUtils.isEmpty(nome) && !StringUtils.isEmpty(cpf))
            return funcionarioRepository.findByNomeContainsOrCpfContains(nome, cpf, pageable);
        else if (!StringUtils.isEmpty(nome) && !StringUtils.isEmpty(email))
            return funcionarioRepository.findByNomeContainsOrEmailContains(nome, email, pageable);
        else if (!StringUtils.isEmpty(cpf) && !StringUtils.isEmpty(email))
            return funcionarioRepository.findByCpfContainsOrEmailContains(cpf, email, pageable);
        else if (!StringUtils.isEmpty(nome))
            return funcionarioRepository.findByNomeContains(nome, pageable);
        else if (!StringUtils.isEmpty(email))
            return funcionarioRepository.findByEmailContains(email, pageable);
        else if (!StringUtils.isEmpty(cpf)) {
            return funcionarioRepository.findByCpfContains(cpf, pageable);
        } else
            return getAll(pageable);
    }

    public Funcionario create(NewFuncionarioDTO dto) {
        Funcionario f = toFuncionario.apply(dto);
        return funcionarioRepository.save(f);
    }

    public Boolean cpfExist(String cpf) {
        return funcionarioRepository.findByCpf(cpf).isPresent();
    }

    @Transactional
    public Funcionario update(Integer id, Funcionario funcionario) {
        Funcionario f = funcionarioRepository.getOne(id);
        f.setCargo(funcionario.getCargo());
        if (funcionario.getCargo().equalsIgnoreCase("gerente") && !funcionario.getRole().equals(Role.GERENTE))
            f.setRole(Role.GERENTE);
        f.setNome(funcionario.getNome());
        f.setTelefone(funcionario.getTelefone());
        return funcionarioRepository.save(f);
    }

    public void updatePassword(Integer id, String password) {
        Funcionario f = funcionarioRepository.getOne(id);
        f.setPassword(encoder.encode(password));
    }

    @Transactional
    public void delete(Integer id) {
        Funcionario f = funcionarioRepository.getOne(id);
        funcionarioRepository.delete(f);
    }

    private Function<NewFuncionarioDTO, Funcionario> toFuncionario = dto -> {
        Funcionario f = new Funcionario();
        f.setUsername(dto.getUsername());
        f.setPassword(encoder.encode(dto.getPassword()));
        f.setNome(dto.getNome());
        f.setCpf(dto.getCpf());
        f.setTelefone(dto.getTelefone());
        f.setEmail(dto.getEmail());
        f.setCargo(dto.getCargo());
        if (dto.getCargo().equalsIgnoreCase("gerente")) {
            f.setRole(Role.GERENTE);
        } else {
            f.setRole(Role.FUNCIONARIO);
        }
        f.setEnable(true);
        f.setConfirmCode(UUID.randomUUID().toString());
        return f;
    };
}
