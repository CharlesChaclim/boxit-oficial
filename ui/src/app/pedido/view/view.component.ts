import { Component, OnInit } from '@angular/core';
import {PedidoService} from '../pedido.service';
import {ActivatedRoute} from '@angular/router';
import {Page} from '../../core/model';
import swal from "sweetalert2";

@Component({
  selector: 'app-view',
  templateUrl: './view.component.html'
})
export class ViewComponent implements OnInit {
  temId: boolean;
  temBoleto: boolean;
  id: number;
  total: number;
  itens: Page;
  boletos: Page;
  constructor(
    private pedidoService: PedidoService,
    private activatedRoute: ActivatedRoute
  ) { }

  ngOnInit() {
    this.activatedRoute.params.subscribe(paramsId => {
      this.id = paramsId.id;
    });
    this.populate();
  }

  populate() {
    this.temId = false;
    this.pedidoService.listPedido().subscribe(
      r => {
        this.itens = r;
        this.total = 0;
        for (const i of this.itens.content) {
          if (i.pedido.id == this.id) {
            this.temId = true;
            this.total += i.subTotal;
          }
        }
      }
    );
    this.temBoleto = false;
    this.pedidoService.listPagamento().subscribe(
      r => {
        this.boletos = r;
        for (const i of this.boletos.content) {
          console.log(this.temBoleto);
          if (i.pedido.id == this.id) {
            this.temBoleto = true;
            break;
          }
        }
      }
    );
  }

  back() {
    history.back();
  }

  help() {
    swal({
        title: 'Ajuda',
        html: 'O botão <button class="btn btn-primary"><i class="fas fa-file-alt"></i></button> permite você emitir um boleto em PDF.' +
          '<br> <br>' +
          'A tabela itens se refere a todos os itens inclusos em seu pedido.' +
          '<br> <br>' +
          'A tabela boletos se refere a todos os boletos vinculados ao seu pedido.' +
          '<br> <br>' +
          'Observação todos os campos são meramente informativos.',
        type: 'info'
      }
    );
  }

}
