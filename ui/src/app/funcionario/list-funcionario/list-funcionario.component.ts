import {Component, OnInit} from '@angular/core';
import {FuncionarioService} from '../funcionario.service';
import {Page} from '../../core/model';
import * as _ from 'lodash';
import {MyMaskUtil} from '../../shared/mask/my-mask.util';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-list-funcionario',
  templateUrl: './list-funcionario.component.html',
  styleUrls: ['./list-funcionario.component.scss']
})
export class ListFuncionarioComponent implements OnInit {
  funcionarios: Page;
  public cpfMask = MyMaskUtil.CPF_MASK_GENERATOR;
  sizes = _.range(5, 51, 5);
  filtred = false;
  name = '';
  cpf = '';
  email = '';
  constructor(
    private funcionarioService: FuncionarioService,
    private toastr: ToastrService
  ) { }

  ngOnInit() {
    this.populate(null, '20');
  }

  populate(page: string, size: string) {
    this.funcionarioService.listAll(page, size).subscribe(
      r => {
        this.funcionarios = r;
      }
    );
  }

  filtrar(page: string, size: string) {
    if (this.name === '' && this.cpf === '' && this.email === '') {
      this.populate(null, '20');
    } else {
      this.filtred = true;
      this.funcionarioService.filtrar(this.name, this.cpf, this.email, page, size).subscribe(
        r =>
          this.funcionarios = r
      );
    }
  }

  loadPage(newPage: string) {
    if (this.filtred) {
      this.filtrar(newPage, this.funcionarios.size.toString());
    } else {
      this.populate(newPage, this.funcionarios.size.toString());
    }
  }

  changeSize(newSize: string) {
    if (this.filtred) {
      this.filtrar(null, newSize);
    } else {
      this.populate(null, newSize);
    }
  }

  updateEnabled(id: string, enable: boolean) {
    this.funcionarioService.updateEnable(id, enable).subscribe(
      r => {
        this.toastr.success('Funcionário atualizado com sucesso!');
        if (this.filtred) {
          this.filtrar((this.funcionarios.number + 1).toString(), this.funcionarios.size.toString());
        } else {
          this.populate((this.funcionarios.number + 1).toString(), this.funcionarios.size.toString());
        }
      }
    );
  }
}
