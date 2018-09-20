package com.uem.boxit.model;

import lombok.Data;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;

@Entity
@Data
public class Cliente extends Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nomeFantasia;

    @CNPJ
    private String cnpj;

    @CPF
    private String cpf;

    @OneToOne
    private Endereco endereco;
}
