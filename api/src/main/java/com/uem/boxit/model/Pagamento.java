package com.uem.boxit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Pagamento {

    @Id
    private Integer id;
    private String nome;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "pedido_id")
    @MapsId
    private Pedido pedido;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPagamento;

    private Double precoPago;
}
