import {Component, OnInit} from '@angular/core';
import {MyMaskUtil} from '../../shared/mask/my-mask.util';
import {Cliente} from '../../core/model';
import {CorreiosService} from '../../shared/correios.service';
import {ClienteService} from '../../cliente/cliente.service';
import {Router} from '@angular/router';
import swal from 'sweetalert2';
import {AuthService} from '../auth.service';
import {ToastrService} from 'ngx-toastr';
import {ErrorHandleService} from '../../core/error-handle.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  public cpfMask = MyMaskUtil.CPF_MASK_GENERATOR;
  public cnpjMask = MyMaskUtil.CNPJ_MASK_GENERATOR;
  public cepMask = MyMaskUtil.CEP_MASK_GENERATOR;
  public phoneMask = MyMaskUtil.DYNAMIC_PHONE_MASK_GENERATOR;
  c = new Cliente();
  cnpjvalid: any;
  emailvalid: any;
  showEmailError = false;
  showCnpjError = false;
  csenha = '';

  constructor(
    private correiosService: CorreiosService,
    private servico: ClienteService,
    private auth: AuthService,
    private router: Router,
    private errHandle: ErrorHandleService,
    private toast: ToastrService,
  ) {
  }

  ngOnInit() {
  }

  buscaEndereco() {
    this.correiosService.getEndereco(this.c.cep).subscribe(
      r => {
        this.c.cidade = r.cidade;
        this.c.logadouro = r.endereco;
        this.c.bairro = r.bairro;
        this.c.estado = r.uf;
      }
    );
  }

  confirmasenha(): boolean {
    return this.c.password !== this.csenha;
  }

  confimacnpj(): boolean {
    this.servico.cnpjExist(this.c.cnpj).subscribe(r => {
        if (r) {
          swal(
            'Erro!',
            'CNPJ já cadastrado',
            'error'
          );
          this.showCnpjError = true;
        }
        this.cnpjvalid = r;
        this.showCnpjError = false;
      }
    );
    return false;
  }

  confimaemail(): boolean {
    this.servico.emailExist(this.c.email).subscribe(r => {
        if (r) {
          swal(
            'Erro!',
            'Email já cadastrado',
            'error'
          );
          this.showEmailError = true;
        }
        this.showEmailError = false;
        this.emailvalid = r;
      }
    );
    return;
  }

  cadastrar() {
    this.auth.create(this.c).then(() => {
      this.router.navigate(['/login']);
      this.toast.info('Verifique o email ' + this.c.email + ' para confirmar sua conta');
    }).catch(err => {
      this.errHandle.handle(err);
    });
  }

  back() {
    history.back();
  }
}
