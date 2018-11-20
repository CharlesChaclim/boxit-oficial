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
        String nomeFantasia = "Joana d'Arc";
        Assertions.assertThat(clienteService.getOne(id).get().getNome()).isEqualTo(nomeFantasia);
    }

    @Test
    public void getOneCnpj(){
        Assertions.assertThat(clienteService.getOneCnpj("78.966.326/0001-65")).isEqualTo(11);
    }


    @Test
    public void create(){
        NewClienteDTO cliente = new NewClienteDTO();
        cliente.setNomeFantasia("Uem | Universidade Estadual De Maringa");
        cliente.setCnpj("78.966.326/0001-65");
        cliente.setCep("87020-900");
        cliente.setComplemento("");
        cliente.setEndereco("Av. Colombo");
        cliente.setBairro("Jd. Universitário");
        cliente.setNumero("5790");
        cliente.setCidade("Maringá");
        cliente.setEstado("Paraná");
        cliente.setNome("Joana d'Arc");
        cliente.setCpf("837.498.090-70");
        cliente.setTelefone("(44) 99828-2751");
        cliente.setEmail("joansdadadaa@gmail.commjhbb");
        cliente.setPassword("NewYor22k");
        Cliente cliente2 = clienteService.create(cliente);
        Assertions.assertThat(cliente2.getNomeFantasia()).isEqualTo("Uem | Universidade Estadual De Maringa");
        Assertions.assertThat(cliente2.getCnpj()).isEqualTo("78.966.326/0001-65");
        Assertions.assertThat(cliente2.getEndereco().getCep()).isEqualTo("87020-900");
        Assertions.assertThat(cliente2.getEndereco().getComplemento()).isEqualTo("");
        Assertions.assertThat(cliente2.getEndereco().getEndereco()).isEqualTo("Av. Colombo");
        Assertions.assertThat(cliente2.getEndereco().getBairro()).isEqualTo("Jd. Universitário");
        Assertions.assertThat(cliente2.getEndereco().getNumero()).isEqualTo("5790");
        Assertions.assertThat(cliente2.getEndereco().getCidade()).isEqualTo("Maringá");
        Assertions.assertThat(cliente2.getEndereco().getEstado()).isEqualTo("Paraná");
        Assertions.assertThat(cliente2.getNome()).isEqualTo("Joana d'Arc");
        Assertions.assertThat(cliente2.getCpf()).isEqualTo("837.498.090-70");
        Assertions.assertThat(cliente2.getTelefone()).isEqualTo("(44) 99828-2751");
        Assertions.assertThat(cliente2.getEmail()).isEqualTo("joansdadadaa@gmail.commjhbb");

    }

    @Test
    public void cnpjExist() {
        String cnpj = "78.966.326/0001-65";
        Assertions.assertThat(clienteService.cnpjExist(cnpj)).isEqualTo(true);
    }

    @Test
    public void update(){
        UpdateClienteDTO cliente = new UpdateClienteDTO();
        cliente.setId(2);
        cliente.setNomeFantasia("Universidade Estadual De Maringa");
        cliente.setCnpj("48.718.420/0001-34");
        Endereco endereco = new Endereco();
        endereco.setCep("87020-900");
        endereco.setComplemento("");
        endereco.setBairro("Jd. Universitário");
        endereco.setNumero("5790");
        endereco.setCidade("Maringá");
        endereco.setEstado("Paraná");
        endereco.setEndereco("Av. Colombo");
        cliente.setEndereco(endereco);
        cliente.setNome("Joana d'Arc");
        cliente.setCpf("837.498.090-70");
        cliente.setTelefone("(44) 99828-2751");
        cliente.setEmail("jodsadadana@gmail.commmm");
        cliente.setPassword("NewYork");
        Cliente cliente2 = clienteService.update(0,cliente);
        Assertions.assertThat(cliente2.getNomeFantasia()).isEqualTo("Universidade Estadual De Maringa");
        Assertions.assertThat(cliente2.getCnpj()).isEqualTo("48.718.420/0001-34");
        Assertions.assertThat(cliente2.getEndereco().getCep()).isEqualTo("87020-900");
        Assertions.assertThat(cliente2.getEndereco().getComplemento()).isEqualTo(null);
        Assertions.assertThat(cliente2.getEndereco().getEndereco()).isEqualTo("Av. Colombo");
        Assertions.assertThat(cliente2.getEndereco().getBairro()).isEqualTo("Jd. Universitário");
        Assertions.assertThat(cliente2.getEndereco().getNumero()).isEqualTo("5790");
        Assertions.assertThat(cliente2.getEndereco().getCidade()).isEqualTo("Maringá");
        Assertions.assertThat(cliente2.getEndereco().getEstado()).isEqualTo("Paraná");
        Assertions.assertThat(cliente2.getNome()).isEqualTo("Joana d'Arc");
        Assertions.assertThat(cliente2.getCpf()).isEqualTo("837.498.090-70");
        Assertions.assertThat(cliente2.getTelefone()).isEqualTo("(44) 99828-2751");
        Assertions.assertThat(cliente2.getEmail()).isEqualTo("jodsadadana@gmail.commmm");
    }

    @Test
    public void filtrar() {
        String nome = "Coca-Cola";
        String cnpj = "30.437.467/0001-64";
        String nomeFantasia = "Coca-Cola";
        Pageable p = new PageRequest(0,20);
        Assertions.assertThat(clienteService.filtrar(nome,cnpj,nomeFantasia,2,p).getContent().get(0).getCnpj()).isEqualTo(cnpj);
        Assertions.assertThat(clienteService.filtrar(nome,cnpj,nomeFantasia,0,p).getContent().get(0).getCnpj()).isEqualTo(cnpj);
    }

    @Test
    public void updateCnpj(){
        Cliente cliente = new Cliente();
        cliente.setCnpj("30.437.467/0001-64");
        Assertions.assertThat(clienteService.updateCnpj(2,cliente).getCnpj()).isEqualTo("30.437.467/0001-64");
    }
}
