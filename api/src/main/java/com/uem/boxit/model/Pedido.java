package com.uem.boxit.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;
    private Double frete;

    private Double preco;
    private Double precoTotal;
    private Double multa;
    private Double juros;
    private Double recebido;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

}
