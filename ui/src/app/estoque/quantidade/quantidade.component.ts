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

  constructor(
    private estoqueService: EstoqueService,
    private router: Router,
    private toastr: ToastrService,
    private activatedRoute: ActivatedRoute
  ) { }

  ngOnInit() {
  }

  qtdPositiva() {
    this.qtd_valida = this.q.qtd < 0;
  }

  precoPositivo() {
    this.preco_valido = this.q.precoCompra < 0;
  }

  cadastrar() {
    if (!((this.q.motivo === 1) || (this.q.motivo === 4))) {
      this.q.qtd *= -1;
    }
    this.estoqueService.atualizarQtd(this.q).subscribe(
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
