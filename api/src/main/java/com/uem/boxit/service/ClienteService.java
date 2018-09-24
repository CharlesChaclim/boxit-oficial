package com.uem.boxit.service;

import com.uem.boxit.dto.NewClienteDTO;
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

    public Page<Cliente> getAllValido(Pageable pageable) {
        return clienteRepository.findByEnableIsTrue(pageable);
    }

    public Optional<Cliente> getOne(Integer id){
        return clienteRepository.findById(id);
    }

    public Cliente create(NewClienteDTO dto){
        Cliente cliente = toCliente.apply(dto);
        return clienteRepository.save(cliente);
    }

    public Boolean cnpjExist(String cnpj) {
        return clienteRepository.findByCnpj(cnpj).isPresent();
    }

    public Boolean emailExist(String email){return clienteRepository.findByEmail(email).isPresent();}

    @Transactional
    public Cliente update(Integer id, Cliente cliente){
        Cliente cli = clienteRepository.getOne(id);
        cli.setNomeFantasia(cliente.getNomeFantasia());
        cli.setEndereco(cliente.getEndereco());
        cli.setCpf(cliente.getCpf());
        cli.setNome(cliente.getNome());
        cli.setTelefone(cliente.getEmail());
        if(cliente.getPassword() != null)
            updatePassword(id, cliente.getPassword());
        return clienteRepository.save(cli);
    }

    @Transactional
    public Cliente updateCnpj(Integer id, Cliente cliente){
        Cliente cli = clienteRepository.getOne(id);
        cli.setCnpj(cliente.getCnpj());
        return clienteRepository.save(cli);
    }

    public void updatePassword(Integer id, String senha){
        Cliente cliente = clienteRepository.getOne(id);
        cliente.setPassword(encoder.encode(senha));
    }

    public void delete(Integer id){
        Cliente cliente = clienteRepository.getOne(id);
        cliente.setEnable(false);
        clienteRepository.save(cliente);
    }

    private Function<NewClienteDTO, Cliente> toCliente = (dto) -> {
        Cliente cliente = new Cliente();
        cliente.setNomeFantasia(dto.getNomeFantasia());
        cliente.setCnpj(dto.getCnpj());
        Endereco endereco = new Endereco(dto.getCep(), dto.getComplemento(), dto.getLogadouro(), dto.getBairro(), dto.getNumero(), dto.getCidade(), dto.getEstado());
        enderecoRepository.save(endereco);
        cliente.setEndereco(endereco);
        cliente.setNome(dto.getNome());
        cliente.setCpf(dto.getCpf());
        cliente.setTelefone(dto.getTelefone());
        cliente.setEmail(dto.getEmail());
        cliente.setUsername(dto.getCnpj().replace(".","").replace("/","").replace("-",""));
        cliente.setEnable(false);
        cliente.setRole(Role.CLIENTE);
        cliente.setConfirmCode(UUID.randomUUID().toString());
        cliente.setPassword(encoder.encode(dto.getPassword()));
        return cliente;
    };
}
