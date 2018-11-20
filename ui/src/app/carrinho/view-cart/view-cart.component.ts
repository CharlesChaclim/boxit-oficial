import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {CarrinhoService} from '../carrinho.service';

@Component({
  selector: 'app-view-cart',
  templateUrl: './view-cart.component.html',
  styleUrls: ['./view-cart.component.scss']
})
export class ViewCartComponent implements OnInit {
  id: string;
  carrinho: any;
  size: any;

  constructor(
    private route: ActivatedRoute,
    private carrinhoService: CarrinhoService
  ) { }

  ngOnInit() {
    this.id = this.route.snapshot.params['id'];
    this.carrinhoService.getOne(this.id).subscribe(
      r => { this.carrinho = r;
        if (this.carrinho.items != null) {
          this.size = this.carrinho.items.length;
        } else {
          this.size = 0;
        }
      }
    );
  }

  remove() {
    this.carrinhoService.remove(this.id).subscribe(
      r => {
        this.carrinhoService.getOne(this.id).subscribe(
          r => { this.carrinho = r; this.size = this.carrinho.items.length; console.log(r); }
        );
      }
    );
  }

}
