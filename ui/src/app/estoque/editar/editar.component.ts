import {Component, OnInit} from '@angular/core';
import {Estoque, EstoqueEdit} from '../../core/model';
import {EstoqueService} from '../estoque.service';
import {CategoriaService} from '../../categoria/categoria.service';
import {ActivatedRoute, Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';
import swal from 'sweetalert2';

@Component({
  selector: 'app-editar',
  templateUrl: './editar.component.html',
  styleUrls: ['./editar.component.scss']
})
export class EditarComponent implements OnInit {
  p = new EstoqueEdit();
  title = 'Visualizar Produto';
  prodID: string;
  name: string;
  sku: string;
  nome_valido = false;
  preco_valido = false;
  desconto_valido = false;
  lote_valido = false;
  categoria: any;
  edit = false;

  constructor(
    private estoqueService: EstoqueService,
    private categoriaService: CategoriaService,
    private router: Router,
    private toastr: ToastrService,
    private activatedRoute: ActivatedRoute
  ) { }

  ngOnInit() {
    this.getCategorias();
    this.edit = this.activatedRoute.snapshot.params['edit'];
    this.activatedRoute.paramMap.subscribe(params => {
      this.prodID = params.get('id');
      if (this.prodID) {
        this.populate();
      } else {
        this.router.navigate(['/estoque']);
      }
    });
    if (this.edit) {
      this.title = 'Editar Produto';
    }
    if (!this.edit) {
      this.edit = false;
    }
  }

  populate() {
    this.estoqueService.getOne(this.prodID).subscribe(
      s => {
        this.p = s;
      }, () => {
        swal(
          'Erro!',
          'Erro no servidor!',
          'error'
        );
      }
    );
  }

  precoPositivo() {
    this.preco_valido = this.p.preco < 0;
  }

  descontoValido() {
    this.desconto_valido = (this.p.desconto >= 0) && (this.p.desconto <= 100);
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

  getCategorias() {
    this.categoriaService.listAll(null, '100').subscribe(
      r => {
        this.categoria = r.content;
      }
    );
  }

  editar() {
    this.estoqueService.update(this.p).subscribe(() => {
        this.router.navigate(['/estoque']);
        this.toastr.success('Produto ' + this.p.nome + ' atualizado com sucesso!');
      }, () => {
        swal('Erro!',
          'Falha no banco de dados\n Tente mais tarde',
          'error');
      }
    );
  }

  back() {
    history.back();
  }

}
