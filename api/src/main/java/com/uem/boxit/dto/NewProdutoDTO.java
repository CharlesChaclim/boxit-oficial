package com.uem.boxit.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NewProdutoDTO {
    private String nome;
    private Double preco;
    private String descricao;
    private Integer unidadeLote;
    private Integer qtd;
    private String sku;
    private List<String> fotos;
    private Integer categoriaId;
    private Double desconto;
}
