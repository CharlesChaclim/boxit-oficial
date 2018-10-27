package com.uem.boxit.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UpdatePagamentoDTO {
    private Date dataPagamento;
    private String nrBoleto;
}
