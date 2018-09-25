import { Component, OnInit } from '@angular/core';
import swal from 'sweetalert2';
import * as _ from 'lodash';
import {Page} from '../../core/model';
import {CategoriaService} from '../categoria.service';

@Component({
  selector: 'app-listar',
  templateUrl: './listar.component.html',
  styleUrls: ['./listar.component.scss']
})
export class ListarComponent implements OnInit {

  cat: Page;
  name = '';
  tamanhos = _.range(5, 51, 5);
  vazio = 0;
  constructor(
    private categoriaService: CategoriaService,
  ) { }

  ngOnInit() {
    this.populate(null, '20');
  }

  populate(pagina: string, tamanho: string) {
    this.categoriaService.listAll(pagina, tamanho).subscribe(
      r => {
        if (r.numberOfElements > 0) {
          this.vazio = 1;
        }
        this.cat = r;
      }
    );
  }

  carregarPagina(novaPagina: string) {
    this.populate(novaPagina, this.cat.size.toString());
  }

  mudarDeTamanho(novoTamanho: string) {
    this.populate(null, novoTamanho);
  }

  desativarAtivar(id: string, enable: boolean, nome: String) {
    this.categoriaService.updateEnable(id, enable).subscribe(
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
      }
    );
  }

}
