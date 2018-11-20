package com.uem.boxit.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateQtdDTO {

    private Integer motivo;
    private String motivoOutro;
    private Integer qtd;
    private Double precoCompra;
}
