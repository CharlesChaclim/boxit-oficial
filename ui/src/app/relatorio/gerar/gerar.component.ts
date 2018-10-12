import {Component, OnInit} from '@angular/core';
import {EstoqueService} from '../../estoque/estoque.service';
import {ClienteService} from '../../cliente/cliente.service';
import {Relatorio} from '../../core/model';
import {RelatorioService} from '../relatorio.service';

@Component({
  selector: 'app-gerar',
  templateUrl: './gerar.component.html',
  styleUrls: ['./gerar.component.scss']
})
export class GerarComponent implements OnInit {
  r = new Relatorio();
  estoque: any;
  cliente: any;
  data_valida = false;

  constructor(
    private relatorioService: RelatorioService,
    private estoqueService: EstoqueService,
    private clienteService: ClienteService,
  ) { }

  ngOnInit() {
    this.getEstoque();
    this.getCliente();
  }

  dataInicioMenorFim() {
    this.data_valida = this.r.dataInicio > this.r.dataFim;
  }

  getEstoque() {
    this.estoqueService.listAll(null, '100').subscribe(
      s => {
        this.estoque = s.content;
      }
    );
  }

  getCliente() {
    this.clienteService.listAll(null, '100').subscribe(
      s => {
        this.cliente = s.content;
      }
    );
  }
}
