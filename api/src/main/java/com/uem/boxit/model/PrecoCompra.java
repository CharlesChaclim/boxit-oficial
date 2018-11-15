package com.uem.boxit.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class PrecoCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAtualizacao;

    private int quantidade;
    private int preco;
}
