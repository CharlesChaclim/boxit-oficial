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
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    @MapsId
    private Pedido pedido;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPagamento;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataVencimento;

    private Boolean pago;
    private Double preco;
    private Double juros;
    private Double multa;
    private Double precoTotal;
    private String nrBoleto;
}
