package com.uem.boxit.dto;

import com.uem.boxit.model.Endereco;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewClienteDTO {

    private String nomeFantasia;
    private String cnpj;
    private Endereco endereco;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private String username;
    private String password;

}
