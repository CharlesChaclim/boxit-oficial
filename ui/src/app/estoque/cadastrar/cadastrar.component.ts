import {Component, OnInit} from '@angular/core';
import {EstoqueService} from '../estoque.service';
import {Estoque} from '../../core/model';
import {MyMaskUtil} from '../../shared/mask/my-mask.util';
import {CategoriaService} from '../../categoria/categoria.service';
import {Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-cadastrar',
  templateUrl: './cadastrar.component.html',
  styleUrls: ['./cadastrar.component.scss']
})
export class CadastrarComponent implements OnInit {
  p = new Estoque();
  name: string;
  sku: string;
  nome_valido = false;
  preco_valido = false;
  qtd_valida = false;
  lote_valido = false;
  sku_valido = false;
  categoria: any;
  public skuMask = MyMaskUtil.SKU_MASK_GENERATOR;

  constructor(
    private estoqueService: EstoqueService,
    private categoriaService: CategoriaService,
    private router: Router,
    private toastr: ToastrService
  ) {}

  ngOnInit() {
    this.p.categoriaId = null;
    this.getCategorias();
  }

  precoPositivo() {
    this.preco_valido = this.p.preco < 0;
  }

  qtdPositiva() {
    this.qtd_valida = this.p.qtd <= 0;
  }

  lotePositivo() {
    this.lote_valido = this.p.unidadeLote <= 0;
  }

  confirmaNome(): boolean {
    if (this.name === this.p.nome) {
      return false;
    } else {
      this.estoqueService.nomeExist(this.p.nome).subscribe(
        p => {
          if (p) {
            this.nome_valido = true;
            return true;
          } else {
            this.nome_valido = false;
            return false;
          }
        }
      );
    }
  }

  confirmaSku(): boolean {
    if (this.sku === this.p.sku) {
      return false;
    } else {
      this.estoqueService.skuExist(this.p.sku).subscribe(
        p => {
          if (p) {
            this.sku_valido = true;
            return true;
          } else {
            this.sku_valido = false;
            return false;
          }
        }
      );
    }
  }

  getCategorias() {
    this.categoriaService.listAll(null, '100').subscribe(
      r => {
        this.categoria = r.content;
      }
    );
  }

  cadastrar() {
    this.estoqueService.criar(this.p).subscribe(
      r => {
        this.router.navigate(['/estoque']);
        this.toastr.success('Produto ' + this.p.nome + ' cadastrado com sucesso!');
      }, e => {
        console.log(e);
      }
    );
  }

  back() {
    history.back();
  }
}
