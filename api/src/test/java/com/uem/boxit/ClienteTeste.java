package com.uem.boxit;

import com.uem.boxit.dto.NewClienteDTO;
import com.uem.boxit.dto.UpdateClienteDTO;
import com.uem.boxit.model.Cliente;
import com.uem.boxit.model.Endereco;
import com.uem.boxit.service.ClienteService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


public class ClienteTeste extends BoxitApplicationTests{

    @Autowired
    private ClienteService clienteService;


    @Test
    public void getAll() {
        Pageable p = new PageRequest(0,20);
        Assertions.assertThat(clienteService.getAll(p).getSize()).isPositive();
    }

    @Test
    public void getOne(){
        int id = 2;
        String nomeFantasia = "Senor Abravanel";
        Assertions.assertThat(clienteService.getOne(id).get().getNome()).isEqualTo(nomeFantasia);
    }

    @Test
    public void getOneCnpj(){
        Assertions.assertThat(clienteService.getOneCnpj("45.997.418/0001-53")).isEqualTo(2);
    }


    @Test
    public void create(){
        NewClienteDTO cliente = new NewClienteDTO();
        String nomeFantasia = "Uem | Universidade Estadual De Maringa";
        String cnpj = "78.966.326/0001-65";
        String cep ="87020-900";
        String complemento ="";
        String endereco ="Av. Colombo";
        String bairro = "Jd. Universitário";
        String numero = "5790";
        String cidade = "Maringá";
        String estado = "Paraná";
        String nome = "Joana d'Arc";
        String cpf = "837.498.090-70";
        String telefone = "(44) 99828-2751";
        String email = "joanadarc@gmail.com";
        cliente.setNomeFantasia(nomeFantasia);
        cliente.setCnpj(cnpj);
        cliente.setCep(cep);
        cliente.setComplemento(complemento);
        cliente.setEndereco(endereco);
        cliente.setBairro(bairro);
        cliente.setNumero(numero);
        cliente.setCidade(cidade);
        cliente.setEstado(estado);
        cliente.setNome(nome);
        cliente.setCpf(cpf);
        cliente.setTelefone(telefone);
        cliente.setEmail(email);
        cliente.setPassword("NewYor22k");
        Cliente cliente2 = clienteService.create(cliente);
        Assertions.assertThat(cliente2.getNomeFantasia()).isEqualTo(nomeFantasia);
        Assertions.assertThat(cliente2.getCnpj()).isEqualTo(cnpj);
        Assertions.assertThat(cliente2.getEndereco().getCep()).isEqualTo(cep);
        Assertions.assertThat(cliente2.getEndereco().getComplemento()).isEqualTo(complemento);
        Assertions.assertThat(cliente2.getEndereco().getEndereco()).isEqualTo(endereco);
        Assertions.assertThat(cliente2.getEndereco().getBairro()).isEqualTo(bairro);
        Assertions.assertThat(cliente2.getEndereco().getNumero()).isEqualTo(numero);
        Assertions.assertThat(cliente2.getEndereco().getCidade()).isEqualTo(cidade);
        Assertions.assertThat(cliente2.getEndereco().getEstado()).isEqualTo(estado);
        Assertions.assertThat(cliente2.getNome()).isEqualTo(nome);
        Assertions.assertThat(cliente2.getCpf()).isEqualTo(cpf);
        Assertions.assertThat(cliente2.getTelefone()).isEqualTo(telefone);
        Assertions.assertThat(cliente2.getEmail()).isEqualTo(email);

    }

    @Test
    public void cnpjExist() {
        String cnpj = "45.997.418/0001-53";
        Assertions.assertThat(clienteService.cnpjExist(cnpj)).isEqualTo(true);
    }

    @Test
    public void update(){
        UpdateClienteDTO cliente = new UpdateClienteDTO();
        int id = 3;
        String nomeFantasia = "Universidade Estadual De Maringa";
        String cnpj = "78.966.326/0001-65";
        String cep ="87020-900";
        String complemento ="";
        String enderecof ="Av. Colombo";
        String bairro = "Jd. Universitário";
        String numero = "5790";
        String cidade = "Maringá";
        String estado = "Paraná";
        String nome = "Joana d'Arc";
        String cpf = "837.498.090-70";
        String telefone = "(44) 99828-2751";
        String email = "joanadarc@gmail.com";
        cliente.setId(id);
        cliente.setNomeFantasia(nomeFantasia);
        cliente.setCnpj(cnpj);
        Endereco endereco = new Endereco();
        endereco.setCep(cep);
        endereco.setComplemento(complemento);
        endereco.setBairro(bairro);
        endereco.setNumero(numero);
        endereco.setCidade(cidade);
        endereco.setEstado(estado);
        endereco.setEndereco(enderecof);
        cliente.setEndereco(endereco);
        cliente.setNome(nome);
        cliente.setCpf(cpf);
        cliente.setTelefone(telefone);
        cliente.setEmail(email);
        cliente.setPassword("NewYork");
        Cliente cliente2 = clienteService.update(id,cliente);
        Assertions.assertThat(cliente2.getNomeFantasia()).isEqualTo(nomeFantasia);
        Assertions.assertThat(cliente2.getCnpj()).isEqualTo(cnpj);
        Assertions.assertThat(cliente2.getEndereco().getCep()).isEqualTo(cep);
        Assertions.assertThat(cliente2.getEndereco().getComplemento()).isEqualTo(complemento);
        Assertions.assertThat(cliente2.getEndereco().getEndereco()).isEqualTo(enderecof);
        Assertions.assertThat(cliente2.getEndereco().getBairro()).isEqualTo(bairro);
        Assertions.assertThat(cliente2.getEndereco().getNumero()).isEqualTo(numero);
        Assertions.assertThat(cliente2.getEndereco().getCidade()).isEqualTo(cidade);
        Assertions.assertThat(cliente2.getEndereco().getEstado()).isEqualTo(estado);
        Assertions.assertThat(cliente2.getNome()).isEqualTo(nome);
        Assertions.assertThat(cliente2.getCpf()).isEqualTo(cpf);
        Assertions.assertThat(cliente2.getTelefone()).isEqualTo(telefone);
        Assertions.assertThat(cliente2.getEmail()).isEqualTo(email);
    }

    @Test
    public void filtrar() {
        String nome = "Senor Abravanel";
        String cnpj = "45.997.418/0001-53";
        String nomeFantasia = "Coca-Cola";
        Pageable p = new PageRequest(0,20);
        Assertions.assertThat(clienteService.filtrar(nome,cnpj,nomeFantasia,2,p).getContent().get(0).getCnpj()).isEqualTo(cnpj);
        Assertions.assertThat(clienteService.filtrar(nome,cnpj,nomeFantasia,0,p).getContent().get(0).getCnpj()).isEqualTo(cnpj);
    }

    @Test
    public void updateCnpj(){
        Cliente cliente = new Cliente();
        String cnpj = "01.582.705/0001-27";
        cliente.setCnpj(cnpj);
        Assertions.assertThat(clienteService.updateCnpj(2,cliente).getCnpj()).isEqualTo(cnpj);
    }
}
