import {Component, OnInit} from '@angular/core';
import {Estoque} from '../../core/model';

@Component({
  selector: 'app-cadastrar',
  templateUrl: './cadastrar.component.html',
  styleUrls: ['./cadastrar.component.scss']
})
export class CadastrarComponent implements OnInit {
  p = new Estoque();
  qtd_valid = false;

  constructor() {
    this.p.qtd = 0;
  }

  ngOnInit() {
  }

  qtdPositiva() {
    this.qtd_valid = this.p.qtd < 0;
  }
}
