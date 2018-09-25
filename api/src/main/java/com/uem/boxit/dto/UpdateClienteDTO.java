package com.uem.boxit.dto;

import com.uem.boxit.model.Endereco;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateClienteDTO {

    private Integer id;
    private String nomeFantasia;
    private String cnpj;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private String password;
    private Endereco endereco;

}
