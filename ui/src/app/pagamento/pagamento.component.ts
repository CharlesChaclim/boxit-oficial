import { Component, OnInit } from '@angular/core';
import {MyMaskUtil} from '../shared/mask/my-mask.util';
import {Cliente} from '../core/model';
import {ClienteService} from '../cliente/cliente.service';
import swal from 'sweetalert2';
import { NgbDateCustomParserFormatter} from '../shared/dateformat';
import {NgbDateParserFormatter} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-pagamento',
  templateUrl: './pagamento.component.html',
  providers: [
    {provide: NgbDateParserFormatter, useClass: NgbDateCustomParserFormatter}
  ]
})
export class PagamentoComponent implements OnInit {
  public cnpjMask = MyMaskUtil.CNPJ_MASK_GENERATOR;
  public boletoMask = MyMaskUtil.BOLETO_MASK_GENERATOR;
  c = new Cliente();
  fboleto: String;
  cnpjvalid = false;
  boletovalid = false;
  showBoletoError = false;
  showCnpjError = false;
  preco_valido = false;
  fpreco: number;
  constructor(private servico: ClienteService) { }

  ngOnInit() {
  }

  precoPositivo() {
    this.preco_valido = this.fpreco < 0;
  }
  confimacnpj(): boolean {
    this.servico.cnpjExist(this.c.cnpj).subscribe(r => {
        if (!r) {
          swal(
            'Erro!',
            'CNPJ não encontrado',
            'error'
          );
          this.showCnpjError = true;
          this.cnpjvalid = true;
        } else {this.cnpjvalid = false; }
        this.showCnpjError = false;
      }
    );
    return false;
  }

  confimaboleto(): boolean {
    this.servico.cnpjExist(this.c.cnpj).subscribe(r => {
        if (!r) {
          swal(
            'Erro!',
            'Boleto não encontrado',
            'error'
          );
          this.showBoletoError = true;
          this.boletovalid = true;
        } else {this.boletovalid = false; }
        this.showBoletoError = false;
      }
    );
    return false;
  }
  back() {
    history.back();
  }
}
