import { Component, OnInit } from '@angular/core';
import {Page} from '../../core/model';
import {PedidoService} from '../pedido.service';

@Component({
  selector: 'app-view',
  templateUrl: './view.component.html'
})
export class ViewComponent implements OnInit {


  itens: Page;
  constructor(
    private pedidoService: PedidoService
  ) { }

  ngOnInit() {
    this.populate();
  }

  populate() {
    this.pedidoService.listPedido('1').subscribe(
      r => {
        this.itens = r;
      }
    );
  }

  back() {
    history.back();
  }

}
