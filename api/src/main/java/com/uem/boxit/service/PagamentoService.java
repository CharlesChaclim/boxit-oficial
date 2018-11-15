package com.uem.boxit.service;


import com.uem.boxit.dto.UpdatePagamentoDTO;
import com.uem.boxit.model.Pagamento;
import com.uem.boxit.model.Pedido;
import com.uem.boxit.repository.PagamentoRepository;
import com.uem.boxit.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.function.Function;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    public Optional<Pagamento> getBoleto(String numero){
        return pagamentoRepository.findByNrBoleto(numero);
    }

    @Transactional
    public Pagamento update(UpdatePagamentoDTO pagamento){
        Pagamento pag = fromUpdateDTO.apply(pagamento);
        return pagamentoRepository.save(pag);
    }

    public Page<Pagamento> getAll(Pageable pageable) {
        return pagamentoRepository.findAll(pageable);
    }

    private Function<UpdatePagamentoDTO, Pagamento> fromUpdateDTO = dto -> {
        Pagamento pagamento = pagamentoRepository.getByNrBoleto(dto.getNrBoleto());
        Pedido pedido = pedidoRepository.getOne(pagamento.getPedido().getId());
        double multa=0;
        double juros;
        double diff = Math.abs(dto.getDataPagamento().getTime() - pagamento.getDataVencimento().getTime());
        double diffDays = Math.ceil(diff / (1000 * 3600 * 24));
        if(diffDays > 0)
            multa = (pagamento.getPreco() * 0.02);
        juros = (diffDays * 0.00033 * pagamento.getPreco());
        pagamento.setMulta(multa);
        pagamento.setJuros(juros);
        pagamento.setDataPagamento(dto.getDataPagamento());
        pagamento.setPago(true);
        pagamento.setPrecoTotal(pagamento.getPreco()+juros+multa);
        pedido.setRecebido(pedido.getRecebido()+pagamento.getPreco()+juros+multa);
        if(multa > 0) {
            pedido.setMulta(pedido.getMulta() + multa);
            pedido.setJuros(pedido.getJuros() + juros);
            pedido.setPrecoTotal(pedido.getPrecoTotal() + juros + multa);
        }
        return pagamento;
    };
}
