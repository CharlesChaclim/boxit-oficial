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
    const splitted = this.fvencimento.toString().split('-', 3);
    const year = toNumbers(splitted[0])[0];
    const month = toNumbers(splitted[1])[0];
    const days = splitted[splitted.length - 1].split('T', 1);
    const day = toNumbers(days[0])[0];
    const fvencimento = new Date(year, month - 1, day);
    const diff = this.p.dataPagamento.getTime() - fvencimento.getTime();
    const diffDays = Math.ceil(diff / (1000 * 3600 * 24));
    if (diffDays > 1) {
      this.fmulta = (this.fpreco * 0.02);
      this.fjuros = (diffDays * 0.00033 * this.fpreco);
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
}
