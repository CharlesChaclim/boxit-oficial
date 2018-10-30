import { Component, OnInit } from '@angular/core';
import {Page} from '../../core/model';
import * as _ from 'lodash';
import {PedidoService} from '../pedido.service';

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

  carregarPagina(novaPagina: string) {
    this.populate(novaPagina, this.pedidos.size.toString());
  }

  mudarDeTamanho(novoTamanho: string) {
      this.populate(null, novoTamanho);
  }
}
