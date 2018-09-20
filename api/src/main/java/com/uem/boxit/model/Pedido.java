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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Usuario user;



}
