package com.uem.boxit;

import com.uem.boxit.dto.NewProdutoDTO;
import com.uem.boxit.model.*;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.uem.boxit.service.ProdutoService;

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

    @Test
    public void salvar() {
        NewProdutoDTO produto = new NewProdutoDTO();
        String nome = "Memória RAM Kingstom DDR3";
        Double preco = 400000.0;
        Double desconto = 0.0;
        Integer qtd = 0;
        Integer lote = 10;
        String sku = "RAM-0004";
        Integer categoriaId = 1;
        String descricao = "Memória antiga";
        produto.setNome(nome);
        produto.setPreco(preco);
        produto.setDesconto(desconto);
        produto.setQtd(qtd);
        produto.setUnidadeLote(lote);
        produto.setSku(sku);
        produto.setCategoriaId(categoriaId);
        produto.setDescricao(descricao);
        Produto produtoTeste = produtoService.salvar(produto);
        Assertions.assertThat(produtoTeste.getNome()).isEqualTo(nome);
        Assertions.assertThat(produtoTeste.getPreco()).isEqualTo(preco);
        Assertions.assertThat(produtoTeste.getDesconto()).isEqualTo(desconto);
        Assertions.assertThat(produtoTeste.getQtd()).isEqualTo(qtd);
        Assertions.assertThat(produtoTeste.getUnidadeLote()).isEqualTo(lote);
        Assertions.assertThat(produtoTeste.getSku()).isEqualTo(sku);
        Assertions.assertThat(produtoTeste.getCategoria().getId()).isEqualTo(categoriaId);
        Assertions.assertThat(produtoTeste.getDescricao()).isEqualTo(descricao);
    }

    @Test
    public void nomeExist() {
        String nome = "Memória RAM Kingstom DDR3";
        Assertions.assertThat(produtoService.nomeExist(nome)).isEqualTo(true);
    }

    @Test
    public void skuExist() {
        String sku = "PRS-7400";
        Assertions.assertThat(produtoService.skuExist(sku)).isEqualTo(true);
    }

    @Test
    public void atualizar() {
        Produto produto = new Produto();
        int id = 1;
        String nome = "Processador Intel Core i7-8700k";
        Double preco = 800000.0;
        Double desconto = 0.0;
        Integer lote = 5;
        String descricao = "Processador muito bom";
        produto.setNome(nome);
        produto.setPreco(preco);
        produto.setDesconto(desconto);
        produto.setUnidadeLote(lote);
        produto.setDescricao(descricao);
        Categoria c = new Categoria();
        String cnome = "mac";
        Integer cid = 1;
        produto.setCategoria(c);
        Produto produtoTeste = produtoService.atualizar(id, produto);
        Assertions.assertThat(produtoTeste.getNome()).isEqualTo(nome);
        Assertions.assertThat(produtoTeste.getPreco()).isEqualTo(preco);
        Assertions.assertThat(produtoTeste.getDesconto()).isEqualTo(desconto);
        Assertions.assertThat(produtoTeste.getUnidadeLote()).isEqualTo(lote);
        Assertions.assertThat(produtoTeste.getDescricao()).isEqualTo(descricao);
    }

    @Test
    public void filtrar() {
        String nome = "Memória RAM Kingstom DDR3";
        String categoria = "Memória RAM";
        Pageable p = new PageRequest(0,20);
        Assertions.assertThat(produtoService.filtrar(nome, categoria,2, p).getContent().get(0).getNome()).isEqualTo(nome);
        Assertions.assertThat(produtoService.filtrar(nome, categoria,0, p).getContent().get(0).getNome()).isEqualTo(nome);
    }
}
