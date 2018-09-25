import {Component, OnInit} from '@angular/core';
import {CategoriaEdit} from '../../core/model';
import {CategoriaService} from '../categoria.service';
import {ActivatedRoute, Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';
import swal from 'sweetalert2';

@Component({
  selector: 'app-editar',
  templateUrl: './editar.component.html',
  styleUrls: ['./editar.component.scss']
})
export class EditarComponent implements OnInit {
  c = new CategoriaEdit();
  name: string;
  catID: string;
  nome_valido = false;
  edit = false;

  constructor(
    private categoriaService: CategoriaService,
    private router: Router,
    private toastr: ToastrService,
    private activatedRoute: ActivatedRoute
  ) { }

  ngOnInit() {
    this.edit = this.activatedRoute.snapshot.params['edit'];
    this.activatedRoute.paramMap.subscribe(params => {
      this.catID = params.get('id');
      if (this.catID) {
        this.populate();
      } else {
        this.router.navigate(['/cliente']);
      }
    });
    if (!this.edit) {
      this.edit = false;
    }
  }

  populate() {
    this.categoriaService.getOne(this.catID).subscribe(
      s => {
        this.c = s;
      }, () => {
        swal(
          'Erro!',
          'Erro no servidor!',
          'error'
        );
      }
    );
  }

  confirmaNome(): boolean {
    if (this.name === this.c.nome) {
      return false;
    } else {
      this.categoriaService.nomeExist(this.c.nome).subscribe(
        c => {
          if (c) {
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

  editar() {
    this.categoriaService.update(this.c).subscribe(() => {
      this.router.navigate(['/cliente']);
      this.toastr.success('Funcionário ' + this.c.nome + ' atualizado com sucesso!');
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
