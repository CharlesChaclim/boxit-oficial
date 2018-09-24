package com.uem.boxit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String cep;
    private String complemento;
    private String logadouro;
    private String bairro;
    private String numero;
    private String cidade;
    private String estado;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id")
    private Usuario user;

    public Endereco(String cep, String complemento, String logadouro, String bairro, String numero, String cidade, String estado) {
        this.cep = cep;
        this.complemento = complemento;
        this.logadouro = logadouro;
        this.bairro = bairro;
        this.numero = numero;
        this.cidade = cidade;
        this.estado = estado;
    }

    public Endereco() { }
}
