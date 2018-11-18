import { Component, OnInit } from '@angular/core';
import {MyMaskUtil} from '../shared/mask/my-mask.util';
import {Pagamento} from '../core/model';
import swal from 'sweetalert2';
import { NgbDateCustomParserFormatter} from '../shared/dateformat';
import {NgbDateParserFormatter} from '@ng-bootstrap/ng-bootstrap';
import {PagamentoService} from '../pagamento.service';
import {toNumbers} from '@angular/compiler-cli/src/diagnostics/typescript_version';
import {Router} from '@angular/router';

@Component({
  selector: 'app-pagamento',
  templateUrl: './pagamento.component.html',
  providers: [
    {provide: NgbDateParserFormatter, useClass: NgbDateCustomParserFormatter}
  ]
})
export class PagamentoComponent implements OnInit {
  public boletoMask = MyMaskUtil.BOLETO_MASK_GENERATOR;
  p = new Pagamento();
  fd: any;
  boletovalid = false;
  datavalid = true;
  fpreco: number;
  fcnpj: string;
  fnome: string;
  criado: Date;
  fmulta: number;
  fjuros: number;
  ftotal: number;
  fvencimento: Date;

  constructor(private servico: PagamentoService,
              private router: Router) { }

  ngOnInit() {
    this.fmulta = 0;
    this.fjuros = 0;
    this.ftotal = 0;
    this.fpreco = 0;
  }
  confimaboleto() {
    this.servico.getBoleto(this.p.nrBoleto).subscribe(r => {
        if (r.pago) {
          swal(
            'Erro!',
            'Boleto já pago',
            'error'
          );
          this.boletovalid = true;
        } else if (r) {
          this.fcnpj = r.pedido.cliente.cnpj;
          this.fnome = r.pedido.cliente.nome;
          this.criado = r.pedido.createAt;
          this.ftotal = this.fpreco = r.preco;
          this.fvencimento = r.dataVencimento;
          this.boletovalid = false;
        }}, () => {
          swal(
            'Erro!',
            'Boleto não encontrado',
            'error'
          );
          this.ftotal = 0;
          this.fpreco = 0;
          this.fcnpj = null;
          this.fnome = null;
          this.boletovalid = true;
        }
    );
    this.fmulta = 0;
    this.fjuros = 0;
    this.fd = 0;
    this.datavalid = true;
  }

  calculaMultaJuros() {
    this.fmulta = 0;
    this.fjuros = 0;
    this.ftotal = 0;
    this.datavalid = false;
    const data = new Date(this.fd.year, this.fd.month - 1, this.fd.day);
    this.p.dataPagamento = data;
    let splitted = this.fvencimento.toString().split('-', 3);
    let year = toNumbers(splitted[0])[0];
    let month = toNumbers(splitted[1])[0];
    let days = splitted[splitted.length - 1].split('T', 1);
    let day = toNumbers(days[0])[0];
    const fvencimento = new Date(year, month - 1, day);
    let diff = this.p.dataPagamento.getTime() - fvencimento.getTime();
    let diffDays = Math.ceil(diff / (1000 * 3600 * 24));
    if (diffDays > 0) {
      this.fmulta = (this.fpreco * 0.02);
      this.fjuros = (diffDays * 0.00033 * this.fpreco);
    } else {
      splitted = this.criado.toString().split('-', 3);
      year = toNumbers(splitted[0])[0];
      month = toNumbers(splitted[1])[0];
      days = splitted[splitted.length - 1].split('T', 1);
      day = toNumbers(days[0])[0];
      const fcriado = new Date(year, month - 1, day);
      diff = this.p.dataPagamento.getTime() - fcriado.getTime();
      diffDays = Math.ceil(diff / (1000 * 3600 * 24));
      if (diffDays < 0) {
        swal('Erro!',
          'Data de pagamento inserido é invalida pois é antes mesmo da criação do boleto',
          'error');
        this.datavalid = true;
        return;
      }
    }
    this.ftotal = this.fpreco + this.fjuros + this.fmulta;
  }

  registrar() {
    this.servico.update(this.p).subscribe(() => {
      swal('Sucesso!',
        'Pagamento Registrado',
        'success');
      }, () => {
      swal('Erro!',
        'Falha no banco de dados\n Tente mais tarde',
        'error');
      });
  }

  back() {
    history.back();
  }

  help() {
    swal({
        title: 'Ajuda',
        html: 'Para dar baixa em um boleto digite o número do mesmo e selecione a data da realização do pagamento.' +
          '<br> <br>' +
          'O campo Preço da Compra se refere ao valor do boleto.' +
          '<br> <br>' +
          'O campo Preço Total se refere ao valor do boleto mais juros e multa caso o boleto seja pague depois do dia do vencimento.' +
          '<br> <br>' +
          'Observação apenas os campos "Número do Boleto" e "Data do Pagamento" são editaveis os outros campos são meramente informativos.',
        type: 'info'
      }
    );
  }
}
