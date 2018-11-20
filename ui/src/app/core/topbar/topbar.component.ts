import {Component, OnInit} from '@angular/core';
import {LogoutService} from '../../auth/logout.service';
import {NavigationEnd, Router} from '@angular/router';
import {AuthService} from '../../auth/auth.service';
import {ClienteService} from '../../cliente/cliente.service';

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

  constructor(private auth: AuthService,
              private router: Router,
              private logoutService: LogoutService,
              private clienteService: ClienteService) {}

  ngOnInit() {
    this.router.events
      .subscribe((event) => {
        if (event instanceof NavigationEnd) {
          this.showHome = this.router.url !== '/';
          this.updateNavbar();
        }
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
        // add aqui
      } else if (payload.authorities[0] === 'CLIENTE') {
        this.ehCliente = true;
        this.clienteService.getOneCnpj(this.auth.jwtPayload.user_name).subscribe((e) => {this.caminho = e; });
      } else if (payload.authorities[0] === 'GERENTE') {
        this.ehGerente = true;
        // add aqui
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
}
