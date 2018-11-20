package com.uem.boxit.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Carrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(fetch = FetchType.EAGER)
    private List<ItemPedido> items;
}
