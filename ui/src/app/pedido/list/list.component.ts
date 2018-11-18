import { Component, OnInit } from '@angular/core';
import {Page} from '../../core/model';
import * as _ from 'lodash';
import {PedidoService} from '../pedido.service';
import swal from 'sweetalert2';
import {AuthService} from '../../auth/auth.service';

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
    private auth: AuthService,
    private pedidoService: PedidoService
  ) { }

  ngOnInit() {
    this.populate(null, '20');
  }

  populate(pagina: string, tamanho: string) {
    this.pedidoService.listCnpj(pagina, tamanho, this.auth.jwtPayload.user_name).subscribe(
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

  help() {
    swal({
        title: 'Ajuda',
        html: 'O botão <button class="btn btn-secondary"><i class="fas fa-truck-moving"></i></button>' +
          ' permite você rastrear os seus pedidos como um todo, para isso você será levado ao site da transportadora.' +
          ' Onde será necessário preencher o seguinte campo com o seu CNPJ ' +
          '<br> <br>' +
          '<img src="../../../assets/img/Ajuda.bmp" style="width: 100%">',
        type: 'info'
      }
    );
  }
}
