package com.uem.boxit.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
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
