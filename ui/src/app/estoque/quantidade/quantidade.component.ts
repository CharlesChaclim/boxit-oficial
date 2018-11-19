import {Component, OnInit} from '@angular/core';
import {AlterarQuantidade} from '../../core/model';
import {EstoqueService} from '../estoque.service';
import {ActivatedRoute, Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-core',
  templateUrl: './quantidade.component.html',
  styleUrls: ['./quantidade.component.scss']
})
export class QuantidadeComponent implements OnInit {
  q = new AlterarQuantidade();
  qtd_valida = false;
  preco_valido = false;
  prodID: string;

  constructor(
    private estoqueService: EstoqueService,
    private router: Router,
    private toastr: ToastrService,
    private activatedRoute: ActivatedRoute
  ) {
    this.activatedRoute.paramMap.subscribe(params => {
      this.prodID = params.get('id');
    });
  }

  ngOnInit() {
  }

  qtdPositiva() {
    this.qtd_valida = this.q.qtd < 0;
  }

  precoPositivo() {
    this.preco_valido = this.q.precoCompra < 0;
  }

  atualizar() {
    this.estoqueService.atualizarQtd(this.q, this.prodID).subscribe(
      r => {
        this.router.navigate(['/estoque']);
        this.toastr.success('Produto atualizado com sucesso!');
      }, e => {
        console.log(e);
      }
    );
  }

  back() {
    history.back();
  }
}
