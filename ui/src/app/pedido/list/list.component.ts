import { Component, OnInit } from '@angular/core';
import {Page} from '../../core/model';
import * as _ from 'lodash';
import {PedidoService} from '../pedido.service';
import swal from 'sweetalert2';
import {a} from '@angular/core/src/render3';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html'
})
export class ListComponent implements OnInit {

  pedidos: Page;
  tamanhos = _.range(5, 51, 5);
  estado = 4;
  vazio = 0;
  constructor(
    private pedidoService: PedidoService
  ) { }

  ngOnInit() {
    this.populate(null, '20');
  }

  populate(pagina: string, tamanho: string) {
    this.pedidoService.listCnpj(pagina, tamanho, '45.997.418/0001-53').subscribe(
      r => {
        if (r.numberOfElements > 0) {
          this.vazio = 1;
        }
        this.pedidos = r;
      }
    );
  }

  rastreamento() {
    swal(  {
      html: 'Clique no botão abaixo e você será direcionado para o site da Transportadora',
      showCloseButton: true,
      confirmButtonText: '<a href="https://www.directlog.com.br/" target="_blank" style="color: #FFFF">Rastrear</a>'
    }
    );
  }

  carregarPagina(novaPagina: string) {
    this.populate(novaPagina, this.pedidos.size.toString());
  }

  mudarDeTamanho(novoTamanho: string) {
      this.populate(null, novoTamanho);
  }
}
