package com.uem.boxit.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RelatorioDTO {
    Date dataInicio;
    Date dataFim;
    String CNPJ;
    Integer tipo;
}
