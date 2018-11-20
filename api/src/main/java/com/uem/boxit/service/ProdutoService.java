package com.uem.boxit.service;

import com.uem.boxit.dto.NewProdutoDTO;
import com.uem.boxit.dto.UpdateQtdDTO;
import com.uem.boxit.model.*;
import com.uem.boxit.repository.*;
import com.uem.boxit.storage.S3;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.cookie.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PrecoCompraRepository precoCompraRepository;

    @Autowired
    private QuantidadeRepository quantidadeRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private HistoricoRepository historicoRepository;

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private S3 s3;

    public Page<Produto> getAll(Pageable pageable) {
        return produtoRepository.findAll(pageable);
    }

    public Optional<Produto> getOne(Integer id) {
        return produtoRepository.findById(id);
    }

    public Produto salvar(NewProdutoDTO dto) {
        Produto p = toProduto.apply(dto);
        return produtoRepository.save(p);
    }

    public Boolean nomeExist(String nome) {
        return produtoRepository.findByNome(nome).isPresent();
    }

    public Boolean skuExist(String sku) {
        return produtoRepository.findBySku(sku).isPresent();
    }

    @Transactional
    public Produto atualizar(Integer id, Produto produto) {
        Produto p = produtoRepository.getOne(id);
        if (!p.getPreco().equals(produto.getPreco())) {
            Historico historico = new Historico();
            Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username;
            if (obj instanceof UserDetails) {
                username = ((UserDetails)obj).getUsername();
            } else {
                username = obj.toString();
            }
            Optional<Funcionario> optional = funcionarioService.getByCnpj(username);
            Usuario user = new Funcionario();
            if (optional.isPresent()) {
                user = optional.get();
            }
            historico.setCreateAt(new Date());
            historico.setPreco(p.getPreco());
            historico.setProduto(p);
            historico.setUser(user);
            historicoRepository.save(historico);
        }
        p.setNome(produto.getNome());
        p.setCategoria(produto.getCategoria());
        p.setPreco(produto.getPreco());
        p.setDescricao(produto.getDescricao());
        p.setUnidadeLote(produto.getUnidadeLote());
        p.setQtd(produto.getQtd());
        p.setDesconto(produto.getDesconto());
        return produtoRepository.save(p);
    }

    public Page<Produto> filtrar(String nome, String categoria, Integer enable, Pageable pageable) {
        if (enable == 2){
            if(!StringUtils.isEmpty(nome) && !StringUtils.isEmpty(categoria))
                return produtoRepository.findByNomeContainsAndCategoria_NomeContainsAndEnableIsTrue(nome, categoria, pageable);
            else if(!StringUtils.isEmpty(nome)){
                return produtoRepository.findByNomeContainsAndEnableIsTrue(nome, pageable);
            }
            else if(!StringUtils.isEmpty(categoria)){
                return produtoRepository.findByCategoria_NomeContainsAndEnableIsTrue(categoria, pageable);
            }
            else return produtoRepository.findByEnableIsTrue(pageable);
        }
        else if(enable == 1){
            if(!StringUtils.isEmpty(nome) && !StringUtils.isEmpty(categoria))
                return produtoRepository.findByNomeContainsAndCategoria_NomeContainsAndEnableIsFalse(nome, categoria, pageable);
            else if(!StringUtils.isEmpty(nome)){
                return produtoRepository.findByNomeContainsAndEnableIsFalse(nome, pageable);
            }
            else if(!StringUtils.isEmpty(categoria)){
                return produtoRepository.findByCategoria_NomeContainsAndEnableIsFalse(categoria, pageable);
            }
            else return produtoRepository.findByEnableIsFalse(pageable);
        }else{
            if(!StringUtils.isEmpty(nome) && !StringUtils.isEmpty(categoria))
                return produtoRepository.findByNomeContainsAndCategoria_NomeContains(nome, categoria, pageable);
            else if(!StringUtils.isEmpty(nome)){
                return produtoRepository.findByNomeContains(nome, pageable);
            }
            else if(!StringUtils.isEmpty(categoria)){
                return produtoRepository.findByCategoria_NomeContains(categoria, pageable);
            }
            else return getAll(pageable);
        }
    }

    @Transactional
    public Produto atualizarQuantidade(Integer id, UpdateQtdDTO qtd) {
        Produto p = produtoRepository.getOne(id);
        Quantidade q = new Quantidade();
        PrecoCompra c = new PrecoCompra();
        Calendar calendario = Calendar.getInstance();
        Date data = calendario.getTime();
        if ((qtd.getMotivo() != 1) && (qtd.getMotivo() != 4)) {
            qtd.setQtd(qtd.getQtd() * -1);
        }
        p.setQtd(qtd.getQtd() + p.getQtd());
        if (qtd.getMotivo() == 1) {
            c.setDataAtualizacao(data);
            c.setPreco(qtd.getPrecoCompra());
            c.setProduto(p);
            c.setQuantidade(qtd.getQtd());
            precoCompraRepository.save(c);
            q.setPreco(qtd.getPrecoCompra());
        } else if (qtd.getMotivo() == 5) {
            q.setMotivoOutro(qtd.getMotivoOutro());
        }
        q.setMotivo(qtd.getMotivo());
        q.setProduto(p);
        q.setQuantidade(qtd.getQtd());
        quantidadeRepository.save(q);
        return produtoRepository.save(p);
    }

    @Transactional
    public void atualizarEnable(Integer id, Boolean enable){
        Produto produto = produtoRepository.getOne(id);
        if(!enable)
            produto.setEnable(true);
        else
            produto.setEnable(false);
        produtoRepository.save(produto);
    }

    private Function<NewProdutoDTO, Produto> toProduto = dto -> {
        Produto p = new Produto();
        p.setCategoria(categoriaRepository.getOne(dto.getCategoriaId()));
        p.setNome(dto.getNome());
        p.setPreco(dto.getPreco());
        p.setDescricao(dto.getDescricao());
        p.setUnidadeLote(dto.getUnidadeLote());
        p.setQtd(0);
        p.setSku(dto.getSku());
        if (dto.getFotos() != null) {
            Map<String, String> fotos = new HashMap<>();
            dto.getFotos().forEach(k -> fotos.put(k, s3.configurarUrl(k)));
            p.setFotos(fotos);
        }
        p.setEnable(true);
        p.setDesconto(0.0);
        return p;
    };
}
