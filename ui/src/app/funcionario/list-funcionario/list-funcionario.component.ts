import {Component, OnInit} from '@angular/core';
import {FuncionarioService} from '../funcionario.service';
import {Page} from '../../core/model';

@Component({
  selector: 'app-list-funcionario',
  templateUrl: './list-funcionario.component.html',
  styleUrls: ['./list-funcionario.component.scss']
})
export class ListFuncionarioComponent implements OnInit {
  funcionarios: Page;
  constructor(
    private funcionarioService: FuncionarioService
  ) { }

  ngOnInit() {
    this.populate();
  }

  populate() {
    this.funcionarioService.listAll().subscribe(
      r => {
        this.funcionarios = r;
      }
    );
  }

  filtrar(name: string, cpf: string, email: string) {
    this.funcionarioService.filtrar(name, cpf, email).subscribe(
      r =>
        console.log(r)
    );
  }
}
