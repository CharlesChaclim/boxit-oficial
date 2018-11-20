package com.uem.boxit;

import com.amazonaws.services.dynamodbv2.xspec.S;
import com.uem.boxit.dto.NewClienteDTO;
import com.uem.boxit.dto.NewProdutoDTO;
import com.uem.boxit.dto.UpdateClienteDTO;
import com.uem.boxit.dto.UpdateQtdDTO;
import com.uem.boxit.model.*;
import com.uem.boxit.service.ClienteService;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.uem.boxit.service.ProdutoService;

import javax.transaction.Transactional;
import java.util.*;
import java.util.function.Function;

public class ProdutoTeste extends BoxitApplicationTests{

    @Autowired
    private ProdutoService produtoService;

    @Test
    public void getAll() {
        Pageable p = new PageRequest(0,20);
        Assertions.assertThat(produtoService.getAll(p).getSize()).isPositive();
    }

    @Test
    public void getOne() {
        int id = 2;
        String nome = "Processador Intel Core i5-7400";
        Assertions.assertThat(produtoService.getOne(id).get().getNome()).isEqualTo(nome);
    }


}
