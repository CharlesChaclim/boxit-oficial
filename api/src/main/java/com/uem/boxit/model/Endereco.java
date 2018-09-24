package com.uem.boxit.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class Endereco implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String cep;
    private String complemento;
    private String endereco;
    private String bairro;
    private String numero;
    private String cidade;
    private String estado;

    public Endereco(String cep, String complemento, String endereco, String bairro, String numero, String cidade, String estado) {
        this.cep = cep;
        this.complemento = complemento;
        this.endereco = endereco;
        this.bairro = bairro;
        this.numero = numero;
        this.cidade = cidade;
        this.estado = estado;
    }

    public Endereco() { }
}
