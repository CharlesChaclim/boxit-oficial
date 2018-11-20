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
  title: string;

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
        this.router.navigate(['/categoria']);
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
        if (this.edit) {
          this.title = 'Editar a categoria ' + this.c.nome;
        } else {
          this.title = 'Detalhes da categoria ' + this.c.nome;
        }
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
      this.router.navigate(['/categoria']);
      this.toastr.success('Categoria ' + this.c.nome + ' atualizado com sucesso!');
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

  help() {
    if (!this.edit) {
      swal({
          title: 'Ajuda',
          html: 'Não é possível editar o conteúdo desta página pois ela é só para visualização e não para edição',
          type: 'info'
        }
      );
    } else {
      swal({
          title: 'Ajuda',
          html: 'Nome é o nome da categoria.' +
            '<br> <br>' +
            'Os campos com * ao seu lado são obrigatórios.' +
            '<br> <br>' +
            'Caso não altere um campo, esse campo manterá os dados atuais.',
          type: 'info'
        }
      );
    }
  }
}
