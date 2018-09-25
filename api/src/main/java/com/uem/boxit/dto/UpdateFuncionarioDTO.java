package com.uem.boxit.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateFuncionarioDTO {

    private Integer id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private String cargo;
    private String username;
    private String password;

}
