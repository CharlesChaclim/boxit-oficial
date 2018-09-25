import {Component, OnInit} from '@angular/core';
import {Categoria} from '../../core/model';
import {CategoriaService} from '../categoria.service';
import {ActivatedRoute, Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';
import {ErrorHandleService} from '../../core/error-handle.service';

@Component({
  selector: 'app-criar',
  templateUrl: './criar.component.html',
  styleUrls: ['./criar.component.scss']
})
export class CriarComponent implements OnInit {
  c = new Categoria();
  name: string;
  nome_valido = false;

  constructor(
    private categoriaService: CategoriaService,
    private router: Router,
    private toastr: ToastrService
  ) { }

  ngOnInit() {
  }

  cadastrar() {
    this.categoriaService.criar(this.c).subscribe(
      r => {
        this.router.navigate(['/categoria']);
        this.toastr.success('Categoria ' + this.c.nome + ' cadastrada com sucesso!');
      }, e => {
        console.log(e);
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

  back() {
    history.back();
  }

}
