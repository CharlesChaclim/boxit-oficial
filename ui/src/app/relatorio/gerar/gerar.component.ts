import {Component, OnInit} from '@angular/core';
import {EstoqueService} from '../../estoque/estoque.service';
import {ClienteService} from '../../cliente/cliente.service';
import {Relatorio} from '../../core/model';
import {RelatorioService} from '../relatorio.service';
import {Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';
import {NgbDateCustomParserFormatter} from '../../shared/dateformat';
import {NgbDateParserFormatter} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-gerar',
  templateUrl: './gerar.component.html',
  styleUrls: ['./gerar.component.scss'],
  providers: [
    {provide: NgbDateParserFormatter, useClass: NgbDateCustomParserFormatter}
  ]
})
export class GerarComponent implements OnInit {
  r = new Relatorio();
  estoque: any;
  cliente: any;
  dataI: any;
  dataF: any;
  inicio = false;
  dataIclick = false;
  dataFclick = false;
  data_valida = false;
  type: number;

  constructor(
    private relatorioService: RelatorioService,
    private estoqueService: EstoqueService,
    private clienteService: ClienteService,
    private router: Router,
    private toastr: ToastrService
  ) { }

  ngOnInit() {
    this.getEstoque();
    this.getCliente();
  }

  dataPreenchidaInicio() {
    this.dataIclick = true;
    this.dataInicioMenorFim();
  }

  dataPreenchidaFim() {
    this.dataFclick = true;
    this.dataInicioMenorFim();
  }

  dataInicioMenorFim() {
    this.data_valida = false;
    if (this.dataIclick && this.dataFclick) {
      this.inicio = true;
      const dataIni = new Date(this.dataI.year, this.dataI.month - 1, this.dataI.day);
      const dataFin = new Date(this.dataF.year, this.dataF.month - 1, this.dataF.day);
      const diff = dataFin.getTime() - dataIni.getTime();
      const diffDays = Math.ceil(diff / (1000 * 3600 * 24));
      if (diffDays >= 0) {
        this.data_valida = true;
      }
    }
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

  /*cadastrar() {
    this.relatorioService.criar(this.r).subscribe(
      s => {
        this.router.navigate(['/relatorio']);
      }, e => {
        console.log(e);
      }
    );
  }*/

  back() {
    history.back();
  }
}
