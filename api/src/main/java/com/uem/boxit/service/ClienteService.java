package com.uem.boxit.service;

import com.uem.boxit.dto.NewClienteDTO;
import com.uem.boxit.dto.UpdateClienteDTO;
import com.uem.boxit.model.Cliente;
import com.uem.boxit.model.Endereco;
import com.uem.boxit.model.enums.Role;
import com.uem.boxit.repository.ClienteRepository;
import com.uem.boxit.repository.EnderecoRepository;
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
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private PasswordEncoder encoder;

    public Page<Cliente> getAll(Pageable pageable) {
        return clienteRepository.findAll(pageable);
    }

    public Optional<Cliente> getOne(Integer id){
        return clienteRepository.findById(id);
    }

    @Transactional
    public Cliente create(NewClienteDTO dto){
        Cliente cliente = toCliente.apply(dto);
        return clienteRepository.save(cliente);
    }

    public Boolean cnpjExist(String cnpj) {
        return clienteRepository.findByCnpj(cnpj).isPresent();
    }

    @Transactional
    public Cliente update(Integer id, UpdateClienteDTO cliente){
        Cliente cli = fromUpdateDTO.apply(cliente);
        return clienteRepository.save(cli);
    }

    public Page<Cliente> filtrar(String nome, String cnpj, String nomeFantasia, Integer enable, Pageable pageable) {
        if (enable == 2){
            if(!StringUtils.isEmpty(nome) && !StringUtils.isEmpty(cnpj) && !StringUtils.isEmpty(nomeFantasia))
                return clienteRepository.findByNomeFantasiaContainsAndNomeContainsAndCnpjContainsAndEnableIsTrue(nomeFantasia,nome,cnpj,pageable);
            else if(!StringUtils.isEmpty(cnpj) && !StringUtils.isEmpty(nome)){
                return clienteRepository.findByCnpjContainsAndEnableIsTrueAndNomeContains(cnpj,nome,pageable);
            }
            else if(!StringUtils.isEmpty(cnpj) && !StringUtils.isEmpty(nomeFantasia)){
                return clienteRepository.findByCnpjContainsAndEnableIsTrueAndNomeFantasiaContains(cnpj,nomeFantasia,pageable);
            }
            else if(!StringUtils.isEmpty(nome) && !StringUtils.isEmpty(nomeFantasia)){
                return clienteRepository.findByNomeContainsAndEnableIsTrueAndNomeFantasiaContains(nome, nomeFantasia, pageable);
            }
            else if(!StringUtils.isEmpty(cnpj)){
                return clienteRepository.findByCnpjContainsAndEnableIsTrue(cnpj, pageable);
            }
            else if(!StringUtils.isEmpty(nome)){
                return clienteRepository.findByNomeContainsAndEnableIsTrue(nome, pageable);
            }
            else if(!StringUtils.isEmpty(nomeFantasia)){
                return clienteRepository.findByNomeFantasiaContainsAndEnableIsTrue(nomeFantasia, pageable);
            }
            else return clienteRepository.findByEnableIsTrue(pageable);
        }
        else if(enable == 1){
            if(!StringUtils.isEmpty(nome) && !StringUtils.isEmpty(cnpj) && !StringUtils.isEmpty(nomeFantasia))
                return clienteRepository.findByNomeFantasiaContainsAndNomeContainsAndCnpjContainsAndEnableIsFalse(nomeFantasia,nome,cnpj,pageable);
            else if(!StringUtils.isEmpty(cnpj) && !StringUtils.isEmpty(nome)){
                return clienteRepository.findByCnpjContainsAndEnableIsFalseAndNomeContains(cnpj,nome,pageable);
            }
            else if(!StringUtils.isEmpty(cnpj) && !StringUtils.isEmpty(nomeFantasia)){
                return clienteRepository.findByCnpjContainsAndEnableIsFalseAndNomeFantasiaContains(cnpj,nomeFantasia,pageable);
            }
            else if(!StringUtils.isEmpty(nome) && !StringUtils.isEmpty(nomeFantasia)){
                return clienteRepository.findByNomeContainsAndEnableIsFalseAndNomeFantasiaContains(nome, nomeFantasia, pageable);
            }
            else if(!StringUtils.isEmpty(cnpj)){
                return clienteRepository.findByCnpjContainsAndEnableIsFalse(cnpj, pageable);
            }
            else if(!StringUtils.isEmpty(nome)){
                return clienteRepository.findByNomeContainsAndEnableIsFalse(nome, pageable);
            }
            else if(!StringUtils.isEmpty(nomeFantasia)){
                return clienteRepository.findByNomeFantasiaContainsAndEnableIsFalse(nomeFantasia, pageable);
            }
            else return clienteRepository.findByEnableIsFalse(pageable);
        }else{
            if(!StringUtils.isEmpty(nome) && !StringUtils.isEmpty(cnpj) && !StringUtils.isEmpty(nomeFantasia))
                return clienteRepository.findByNomeFantasiaContainsAndNomeContainsAndCnpjContains(nomeFantasia,nome,cnpj,pageable);
            else if(!StringUtils.isEmpty(cnpj) && !StringUtils.isEmpty(nome)){
                return clienteRepository.findByCnpjContainsAndNomeContains(cnpj,nome,pageable);
            }
            else if(!StringUtils.isEmpty(cnpj) && !StringUtils.isEmpty(nomeFantasia)){
                return clienteRepository.findByCnpjContainsAndNomeFantasiaContains(cnpj,nomeFantasia,pageable);
            }
            else if(!StringUtils.isEmpty(nome) && !StringUtils.isEmpty(nomeFantasia)){
                return clienteRepository.findByNomeContainsAndNomeFantasiaContains(nome, nomeFantasia, pageable);
            }
            else if(!StringUtils.isEmpty(cnpj)){
                return clienteRepository.findByCnpjContains(cnpj, pageable);
            }
            else if(!StringUtils.isEmpty(nome)){
                return clienteRepository.findByNomeContains(nome, pageable);
            }
            else if(!StringUtils.isEmpty(nomeFantasia)){
                return clienteRepository.findByNomeFantasiaContains(nomeFantasia, pageable);
            }
            else return getAll(pageable);
        }
    }

    @Transactional
    public Cliente updateCnpj(Integer id, Cliente cliente){
        Cliente cli = clienteRepository.getOne(id);
        cli.setCnpj(cliente.getCnpj());
        return clienteRepository.save(cli);
    }

    @Transactional
    public void updatePassword(Integer id, String senha){
        Cliente cliente = clienteRepository.getOne(id);
        cliente.setPassword(encoder.encode(senha));
        cliente.setPasswordResetCode(null);
        cliente.setPasswordRestCodeExpiryDate(null);
        clienteRepository.save(cliente);
    }

    @Transactional
    public void atualizarEnable(Integer id, Boolean enable){
        Cliente cliente = clienteRepository.getOne(id);
        if(!enable)
            cliente.setEnable(true);
        else
            cliente.setEnable(false);
        clienteRepository.save(cliente);
    }

    public void rollback(Cliente c) {
        clienteRepository.delete(c);
    }

    private Function<NewClienteDTO, Cliente> toCliente = dto -> {
        Cliente cliente = new Cliente();
        cliente.setNomeFantasia(dto.getNomeFantasia());
        cliente.setCnpj(dto.getCnpj());
        Endereco endereco = new Endereco(dto.getCep(), dto.getComplemento(), dto.getEndereco(), dto.getBairro(), dto.getNumero(), dto.getCidade(), dto.getEstado());
        enderecoRepository.save(endereco);
        cliente.setEndereco(endereco);
        cliente.setNome(dto.getNome());
        cliente.setCpf(dto.getCpf());
        cliente.setTelefone(dto.getTelefone());
        cliente.setEmail(dto.getEmail());
        cliente.setUsername(dto.getCnpj());
        cliente.setEnable(false);
        cliente.setRole(Role.CLIENTE);
        cliente.setConfirmCode(UUID.randomUUID().toString());
        cliente.setPassword(encoder.encode(dto.getPassword()));
        return cliente;
    };

    private Function<UpdateClienteDTO, Cliente> fromUpdateDTO = dto -> {
        Cliente cliente = clienteRepository.getOne(dto.getId());
        cliente.setNomeFantasia(dto.getNomeFantasia());
        enderecoRepository.save(dto.getEndereco());
        cliente.setNome(dto.getNome());
        cliente.setCpf(dto.getCpf());
        cliente.setCnpj(dto.getCnpj());
        cliente.setUsername(dto.getCnpj());
        cliente.setTelefone(dto.getTelefone());
        cliente.setEmail(dto.getEmail());
        if(dto.getPassword() != null) {
            cliente.setPassword(encoder.encode(dto.getPassword()));
        }
        return cliente;
    };
}
