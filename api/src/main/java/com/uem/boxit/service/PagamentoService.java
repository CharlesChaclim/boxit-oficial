package com.uem.boxit.service;


import com.uem.boxit.dto.UpdatePagamentoDTO;
import com.uem.boxit.model.Pagamento;
import com.uem.boxit.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.function.Function;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    public Optional<Pagamento> getBoleto(String numero){
        return pagamentoRepository.findByNrBoleto(numero);
    }

    @Transactional
    public Pagamento update(UpdatePagamentoDTO pagamento){
        Pagamento pag = fromUpdateDTO.apply(pagamento);
        return pagamentoRepository.save(pag);
    }

    private Function<UpdatePagamentoDTO, Pagamento> fromUpdateDTO = dto -> {
        Pagamento pagamento = pagamentoRepository.getByNrBoleto(dto.getNrBoleto());
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
        return pagamento;
    };
}
