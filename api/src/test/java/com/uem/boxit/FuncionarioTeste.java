package com.uem.boxit;

import com.uem.boxit.dto.NewClienteDTO;
import com.uem.boxit.dto.NewFuncionarioDTO;
import com.uem.boxit.dto.UpdateClienteDTO;
import com.uem.boxit.model.Cliente;
import com.uem.boxit.model.Endereco;
import com.uem.boxit.model.Funcionario;
import com.uem.boxit.service.ClienteService;
import com.uem.boxit.service.FuncionarioService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


public class FuncionarioTeste extends BoxitApplicationTests{

    @Autowired
    private FuncionarioService funcionarioService;


    @Test
    public void getAll() {
        Pageable p = new PageRequest(0,20);
        Assertions.assertThat(funcionarioService.getAll(p).getSize()).isPositive();
    }

    @Test
    public void getOne(){
        int id = 1;
        String email = "leonardo.c.a.p2@gmail.com";
        Assertions.assertThat(funcionarioService.getOne(id).get().getEmail()).isEqualTo(email);
    }

    @Test
    public void getOneCnpj(){
        String cpf = "093.003.869-05";
        Assertions.assertThat(funcionarioService.getByCnpj("093.003.869-05").get().getCpf()).isEqualTo(cpf);
    }


    @Test
    public void create(){
        NewFuncionarioDTO dto = new NewFuncionarioDTO();
        String nome = "Leonardo";
        String cpf = "093.003.869-05";
        String telefone = "(44) 99853-8145";
        String email = "leonardo.c.a.p2@gmail.com";
        String carrgo = "gerente";
        String password = "123";
        dto.setNome(nome);
        dto.setCpf(cpf);
        dto.setTelefone(telefone);
        dto.setEmail(email);
        dto.setCargo(carrgo);
        dto.setPassword(password);
        Funcionario fun = funcionarioService.create(dto);
        Assertions.assertThat(fun.getNome()).isEqualTo(nome);
        Assertions.assertThat(fun.getCpf()).isEqualTo(cpf);
        Assertions.assertThat(fun.getTelefone()).isEqualTo(telefone);
        Assertions.assertThat(fun.getEmail()).isEqualTo(email);
        Assertions.assertThat(fun.getCargo()).isEqualTo(carrgo);

    }

    @Test
    public void cpfExist() {
        String cpf = "093.003.869-05";
        Assertions.assertThat(funcionarioService.cpfExist(cpf)).isEqualTo(true);
    }
}