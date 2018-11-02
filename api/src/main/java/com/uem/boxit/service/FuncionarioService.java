package com.uem.boxit.service;

import com.uem.boxit.dto.NewFuncionarioDTO;
import com.uem.boxit.dto.UpdateFuncionarioDTO;
import com.uem.boxit.model.Funcionario;
import com.uem.boxit.model.Usuario;
import com.uem.boxit.model.enums.Role;
import com.uem.boxit.repository.FuncionarioRepository;
import com.uem.boxit.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@Service
public class FuncionarioService {
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

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
            return funcionarioRepository.findByNomeContainsAndCpfContainsAndEmailContains(nome, cpf, email, pageable);
        else if (!StringUtils.isEmpty(nome) && !StringUtils.isEmpty(cpf))
            return funcionarioRepository.findByNomeContainsAndCpfContains(nome, cpf, pageable);
        else if (!StringUtils.isEmpty(nome) && !StringUtils.isEmpty(email))
            return funcionarioRepository.findByNomeContainsAndEmailContains(nome, email, pageable);
        else if (!StringUtils.isEmpty(cpf) && !StringUtils.isEmpty(email))
            return funcionarioRepository.findByCpfContainsAndEmailContains(cpf, email, pageable);
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
    public Funcionario update(Integer id, UpdateFuncionarioDTO funcionario) {
        Funcionario f = fromUpdateDTO.apply(funcionario);
        return funcionarioRepository.save(f);
    }

    public void updatePassword(Integer id, String password) {
        Funcionario f = funcionarioRepository.getOne(id);
        f.setPassword(encoder.encode(password));
        funcionarioRepository.save(f);
    }

    @Transactional
    public void delete(Integer id) {
        Funcionario f = funcionarioRepository.getOne(id);
        funcionarioRepository.delete(f);
    }

    private Function<NewFuncionarioDTO, Funcionario> toFuncionario = dto -> {
        Funcionario f = new Funcionario();
        f.setUsername(dto.getCpf());
        f.setPassword(encoder.encode(dto.getPassword()));
        f.setNome(dto.getNome());
        f.setCpf(dto.getCpf());
        f.setTelefone(dto.getTelefone());
        f.setEmail(dto.getEmail());
        f.setCargo(dto.getCargo());
        if (StringUtils.containsIgnoreCase(dto.getCargo(), "gerente")) {
            f.setRole(Role.GERENTE);
        } else {
            f.setRole(Role.FUNCIONARIO);
        }
        f.setEnable(true);
        f.setConfirmCode(UUID.randomUUID().toString());
        return f;
    };

    private Function<UpdateFuncionarioDTO, Funcionario> fromUpdateDTO = dto -> {
        Funcionario f = funcionarioRepository.getOne(dto.getId());
        if(isGerente())
            f.setCargo(dto.getCargo());
        Boolean containGerente = StringUtils.containsIgnoreCase(dto.getCargo(), "gerente");
        if (!IsMe(dto.getId()) && containGerente && !f.getRole().equals(Role.GERENTE))
            f.setRole(Role.GERENTE);
        else if (!IsMe(dto.getId()) && isGerente() && !containGerente && !f.getRole().equals(Role.FUNCIONARIO))
            f.setRole(Role.FUNCIONARIO);
        f.setNome(dto.getNome());
        f.setTelefone(dto.getTelefone());
        if (dto.getPassword() != null)
            f.setPassword(encoder.encode(dto.getPassword()));
        f.setUsername(dto.getCpf());
        f.setEmail(dto.getEmail());
        return f;
    };

    private boolean isGerente() {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        Optional<Usuario> user = usuarioRepository.findByUsername(username);
        return user.map(usuario -> usuario.getRole().equals(Role.GERENTE)).orElse(false);
    }

    private boolean IsMe(Integer id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        Optional<Usuario> user = usuarioRepository.findByUsername(username);
        return user.map(usuario -> usuario.getId().equals(id)).orElse(false);
    }
}
