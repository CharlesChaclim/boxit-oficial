package com.uem.boxit.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Quantidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    private int quantidade;
    private double preco;
    private int motivo;
    private String motivoOutro;
}
