import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, NavigationEnd, Router} from '@angular/router';
import {EstoqueService} from '../../estoque/estoque.service';
import {Carrinho, Page} from '../../core/model';
import * as _ from 'lodash';
import {CarrinhoService} from '../../carrinho/carrinho.service';
import {ClienteService} from '../../cliente/cliente.service';
import {AuthService} from '../../auth/auth.service';

@Component({
  selector: 'app-list-produto',
  templateUrl: './list-produto.component.html',
  styleUrls: ['./list-produto.component.scss']
})
export class ListProdutoComponent implements OnInit {
  id: string;
  clienteId: any;
  name: string;
  nome = '';
  produtos: any;
  prod: Page;
  tamanhos = _.range(5, 51, 5);
  enable = 2;
  filtrado = false;
  qtd = 1;
  carrinho = new Carrinho();

  constructor(
    private route: ActivatedRoute,
    private estoqueService: EstoqueService,
    private carrinhoService: CarrinhoService,
    private clienteService: ClienteService,
    private auth: AuthService
  ) {}

  ngOnInit() {
    this.id = this.route.snapshot.params['cat'];
    this.name = this.route.snapshot.params['name'];
    this.filtrar(null, '20');
  }

  filtrar(pagina: string, tamanho: string) {
    this.filtrado = true;
    this.estoqueService.filtrar(this.nome, this.name, this.enable, pagina, tamanho).subscribe(
      r => {
        this.prod = r;
        this.produtos = r.content;
      }
    );
  }
  loadPage(newPage: string) {
    if (this.filtrado) {
      this.filtrar(newPage, this.prod.size.toString());
    } else {
      this.filtrar(newPage, this.prod.size.toString());
    }
  }

  add() {
    this.qtd += 1;
  }

  minus() {
    if ( (this.qtd - 1) <= 0) {
      this.qtd = 0;
    } else {
      this.qtd -= 1;
    }
  }

  mudarDeTamanho(novoTamanho: string) {
    if (this.filtrado) {
      this.filtrar(null, novoTamanho);
    } else {
      this.filtrar(null, novoTamanho);
    }
  }

  addToCart(prodId: string, qtd: string) {
    this.clienteService.getOneCnpj(this.auth.jwtPayload.user_name).subscribe((e) => {
      this.clienteId = e;
      this.carrinho.id = this.clienteId;
      this.carrinho.produtoId = prodId;
      this.carrinho.qty = qtd;
      this.carrinhoService.add(this.carrinho).subscribe(
        r => console.log(r)
      );
    });
  }
}
