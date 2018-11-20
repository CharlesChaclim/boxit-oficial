import {Component, OnInit} from '@angular/core';
import {Page} from '../../core/model';
import * as _ from 'lodash';
import swal from 'sweetalert2';
import {EstoqueService} from '../estoque.service';
import {CategoriaService} from '../../categoria/categoria.service';

@Component({
  selector: 'app-listar',
  templateUrl: './listar.component.html',
  styleUrls: ['./listar.component.scss']
})
export class ListarComponent implements OnInit {

  prod: Page;
  name = '';
  categoria = '';
  tamanhos = _.range(5, 51, 5);
  vazio = 0;
  enable = 3;
  filtrado = false;
  constructor(
    private estoqueService: EstoqueService,
    private categoriaService: CategoriaService
  ) { }

  ngOnInit() {
    this.populate(null, '20');
    this.getCategorias();
  }

  populate(pagina: string, tamanho: string) {
    this.estoqueService.listAll(pagina, tamanho).subscribe(
      r => {
        if (r.numberOfElements > 0) {
          this.vazio = 1;
        }
        this.prod = r;
      }
    );
  }

  getCategorias() {
    this.categoriaService.listAll(null, '100').subscribe(
      r => {
        this.categoria = r.content;
      }
    );
  }

  filtrar(pagina: string, tamanho: string) {
    if (this.name === '' && this.categoria === '' && this.enable === 3) {
      this.populate(null, '20');
    } else {
      this.filtrado = true;
      this.estoqueService.filtrar(this.name, '', this.enable, pagina, tamanho).subscribe(
        r =>
          this.prod = r
      );
    }
  }

  filtrarWithCategoria(pagina: string, tamanho: string, categoria) {
    if (this.name === '' && this.categoria === '' && this.enable === 3) {
      this.populate(null, '20');
    } else {
      this.filtrado = true;
      this.estoqueService.filtrar(this.name, categoria, this.enable, pagina, tamanho).subscribe(
        r =>
          this.prod = r
      );
    }
  }

  carregarPagina(novaPagina: string) {
    if (this.filtrado) {
      this.filtrar(novaPagina, this.prod.size.toString());
    } else {
      this.populate(novaPagina, this.prod.size.toString());
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
    this.estoqueService.updateEnable(id, enable).subscribe(
      () => {
        if (enable) {
          swal(
            'Sucesso!',
            'Produto ' + nome + ' desativado com sucesso!',
            'success'
          );
        } else {
          swal(
            'Sucesso!',
            'Produto ' + nome + ' ativado com sucesso!',
            'success'
          );
        }
        if (this.filtrado) {
          this.filtrar((this.prod.number + 1).toString(), this.prod.size.toString());
        } else {
          this.populate((this.prod.number + 1).toString(), this.prod.size.toString());
        }
      }
    );
  }

  help() {
    swal({
      title: 'Ajuda',
      html: 'O botão <button class="btn btn-primary"><i class="fal fa-eye"></i></button> permite você ver o cadastro do produto.' +
        '<br> <br> ' +
        'O botão <button class="btn btn-secondary"><i class="fal fa-edit"></i></button> permite você editar o cadastro do produto.' +
        '<br> <br>' +
        'O botão <button class="btn btn-danger"><i class="fal fa-box-usd"></i></button> permite você alterar ' +
        'a quantidade do produto cadastrado.' +
        '<br> <br>' +
        'O botão <span class="badge badge-pill badge-success">Ativo</span>/<span class="badge badge-pill badge-warning">Inativo</span> ' +
        'permite você desativar/ativar o produto, assim como informar o estado do mesmo.',
      type: 'info'}
    );
  }
}
