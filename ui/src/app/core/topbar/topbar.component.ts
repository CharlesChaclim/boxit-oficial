import {Component, OnInit} from '@angular/core';
import {LogoutService} from '../../auth/logout.service';
import {NavigationEnd, Router} from '@angular/router';
import {AuthService} from '../../auth/auth.service';
import {ClienteService} from '../../cliente/cliente.service';
import {FuncionarioService} from '../../funcionario/funcionario.service';
import {CategoriaService} from '../../categoria/categoria.service';

@Component({
  selector: 'app-topbar',
  templateUrl: './topbar.component.html',
  styleUrls: ['./topbar.component.scss']
})
export class TopbarComponent implements OnInit {
  navbar = false;
  ehFuncionario = false;
  ehCliente = false;
  ehGerente = false;
  caminho: any;
  showHome = false;
  payload: any;
  categoria: any;
  home: any;

  constructor(
    private auth: AuthService,
    private router: Router,
    private logoutService: LogoutService,
    private clienteService: ClienteService,
    private funcionarioService: FuncionarioService,
    private categoriaService: CategoriaService
  ) {
  }

  ngOnInit() {
    this.router.events
      .subscribe((event) => {
        if (event instanceof NavigationEnd) {
          this.showHome = this.router.url !== '/';
          this.updateNavbar();
        }
        this.payload = this.auth.jwtPayload;
        let authorit = '';
        if (this.payload === undefined || this.payload === null) {
          authorit = null;
        } else {
          authorit = this.payload.authorities[0];
        }
        if (authorit === 'GERENTE' || authorit === 'FUNCIONARIO') {
          this.home = 'estoque';
        } else if (authorit === 'CLIENTE') {
          this.home = 'pedido';
        } else {
          this.home = 'login';
        }
        this.categoriaService.listAll(null, '100').subscribe(
           r => { this.categoria = r.content; }
        );
      });
  }

  toggle() {
    this.navbar = !this.navbar;
  }

  private updateNavbar() {
    const payload = this.auth.jwtPayload;
    if (payload != null) {
      this.showHome = false;
      if (payload.authorities[0] === 'FUNCIONARIO') {
        this.ehFuncionario = true;
        this.funcionarioService.getByCpf(this.auth.jwtPayload.user_name).subscribe(
          e => {this.caminho = e['id']; }
        );
      } else if (payload.authorities[0] === 'CLIENTE') {
        this.ehCliente = true;
        this.clienteService.getOneCnpj(this.auth.jwtPayload.user_name).subscribe((e) => {
          this.caminho = e;
        });
      } else if (payload.authorities[0] === 'GERENTE') {
        this.ehGerente = true;
        this.funcionarioService.getByCpf(this.auth.jwtPayload.user_name).subscribe(
          e => {this.caminho = e['id']; }
        );
      }
    } else {
      this.ehFuncionario = false;
      this.ehCliente = false;
      this.ehGerente = false;
      this.showHome = true;
    }
  }

  logout() {
    this.ehFuncionario = false;
    this.ehCliente = false;
    this.ehGerente = false;
    this.showHome = true;
    this.auth.jwtPayload = null;
    this.logoutService.logout()
      .then(() => {
        this.router.navigate(['/login']);
      });
  }

  reload() {
    window.location.reload();
  }
}
