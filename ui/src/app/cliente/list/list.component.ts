import { Component, OnInit } from '@angular/core';
import {Page} from '../../core/model';
import {MyMaskUtil} from '../../shared/mask/my-mask.util';
import * as _ from 'lodash';
import {ClienteService} from '../cliente.service';
import swal from 'sweetalert2';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html'
})
export class ListComponent implements OnInit {

  clientes: Page;
  public cnpjMask = MyMaskUtil.CNPJ_MASK_GENERATOR;
  tamanhos = _.range(5, 51, 5);
  name = '';
  cnpj = '';
  nomeFantasia = '';
  enable = 3;
  filtrado = false;
  vazio = 0;
  constructor(
    private clienteService: ClienteService
  ) { }

  ngOnInit() {
    this.populate(null, '20');
  }

  populate(pagina: string, tamanho: string) {
    this.clienteService.filtrar('', '', '', 0, pagina, tamanho).subscribe(
      r => {
        if (r.numberOfElements > 0) {
          this.vazio = 1;
        }
        this.clientes = r;
      }
    );
  }

  filtrar(pagina: string, tamanho: string) {
    if (this.name === '' && this.cnpj === '' && this.nomeFantasia === '' && this.enable === 3) {
      this.populate(null, '20');
    } else {
      this.filtrado = true;
      this.clienteService.filtrar(this.name, this.cnpj, this.nomeFantasia, this.enable, pagina, tamanho).subscribe(
        r =>
          this.clientes = r
      );
    }
  }

  carregarPagina(novaPagina: string) {
    if (this.filtrado) {
      this.filtrar(novaPagina, this.clientes.size.toString());
    } else {
      this.populate(novaPagina, this.clientes.size.toString());
    }
  }

  mudarDeTamanho(novoTamanho: string) {
    if (this.filtrado) {
      this.filtrar(null, novoTamanho);
    } else {
      this.populate(null, novoTamanho);
    }
  }

  desativarAtivar(id: string, enable: boolean, nome: String) {
    this.clienteService.atualizarEnable(id, enable).subscribe(
      () => {
        if (enable) {
          swal(
            'Sucesso!',
            'Cliente ' + nome + ' desativado com sucesso!',
            'success'
          );
        } else {
          swal(
            'Sucesso!',
            'Cliente ' + nome + ' ativado com sucesso!',
            'success'
          );
        }
        if (this.filtrado) {
          this.filtrar((this.clientes.number + 1).toString(), this.clientes.size.toString());
        } else {
          this.populate((this.clientes.number + 1).toString(), this.clientes.size.toString());
        }
      }
    );
  }

  help() {
    swal({
      title: 'Ajuda',
      html: 'O botão <button class="btn btn-primary"><i class="fal fa-eye"></i></button> permite você ver o cadastro do cliente.' +
        '<br> <br> ' +
      'O botão <button class="btn btn-secondary"><i class="fal fa-edit"></i></button> permite você editar o cadastro do cliente.' +
        '<br> <br>' +
        'O botão <span class="badge badge-pill badge-success">Ativo</span>/<span class="badge badge-pill badge-warning">Inativo</span> ' +
        'permite você desativar/ativar o cadastro do cliente, assim como informar o estado do cadastro.',
      type: 'info'}
    );
  }
}
