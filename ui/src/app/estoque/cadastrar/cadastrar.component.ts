import {Component, OnInit} from '@angular/core';
import {EstoqueService} from '../estoque.service';
import {Estoque} from '../../core/model';
import {MyMaskUtil} from '../../shared/mask/my-mask.util';
import {CategoriaService} from '../../categoria/categoria.service';

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
  qtd_valida = false;
  lote_valido = false;
  sku_valido = false;
  public skuMask = MyMaskUtil.SKU_MASK_GENERATOR;

  constructor(
    private estoqueService: EstoqueService,
    private categoriaService: CategoriaService
  ) {}

  ngOnInit() {
  }

  qtdPositiva() {
    this.qtd_valida = this.p.qtd < 0;
  }

  lotePositivo() {
    this.lote_valido = this.p.unidadeLote < 0;
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

  back() {
    history.back();
  }
}
