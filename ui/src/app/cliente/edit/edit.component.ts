import {Component, OnInit} from '@angular/core';
import {MyMaskUtil} from '../../shared/mask/my-mask.util';
import {Cliente} from '../../core/model';
import {CorreiosService} from '../../shared/correios.service';
import {ClienteService} from '../cliente.service';
import {ActivatedRoute, Router} from '@angular/router';
import swal from 'sweetalert2';
import {ToastrService} from 'ngx-toastr';
import {ErrorHandleService} from '../../core/error-handle.service';

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.scss']
})
export class EditComponent implements OnInit {
  public cpfMask = MyMaskUtil.CPF_MASK_GENERATOR;
  public cnpjMask = MyMaskUtil.CNPJ_MASK_GENERATOR;
  public cepMask = MyMaskUtil.CEP_MASK_GENERATOR;
  public phoneMask = MyMaskUtil.DYNAMIC_PHONE_MASK_GENERATOR;
  c = new Cliente();
  cliId: string;
  cnpjvalid = false;
  emailvalid = false;
  showEmailError = false;
  showCnpjError = false;
  csenha = null;
  update = false;

  constructor(
    private correiosService: CorreiosService,
    private servico: ClienteService,
    private router: Router,
    private errHandle: ErrorHandleService,
    private toast: ToastrService,
    private activatedRoute: ActivatedRoute
  ) {
  }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(params => {
      this.cliId = params.get('id');
      if (this.cliId) {
        this.update = true;
        this.servico.getOne(this.cliId).subscribe(
          (s) => {
            console.log(s);
            this.c = s;
            this.c.password = null;
            this.c.cep = s.endereco['cep'];
            this.c.bairro = s.endereco['bairro'];
            this.c.cidade = s.endereco['cidade'];
            this.c.numero = s.endereco['numero'];
            this.c.endereco = s.endereco['endereco'];
            this.c.estado = s.endereco['estado'];
            this.c.complemento = s.endereco['complemento'];
          }, () => {
            swal(
              'Erro!',
              'Erro no servidor!',
              'error'
            );
          }
        );
      } else {
        this.router.navigate(['/cliente']);
      }
    });
  }

  buscaEndereco() {
    this.correiosService.getEndereco(this.c.cep).subscribe(
      r => {
        this.c.cidade = r.cidade;
        this.c.endereco = r.endereco;
        this.c.bairro = r.bairro;
        this.c.estado = r.uf;
      }
    );
  }

  confirmasenha(): boolean {
    return this.c.password !== this.csenha;
  }

  confimacnpj(): boolean {
    this.servico.getOne(this.cliId).subscribe(e => {
      if (e.cnpj === this.c.cnpj) {
        return false;
      } else {
        this.servico.cnpjExist(this.c.cnpj).subscribe(r => {
            if (r) {
              swal(
                'Erro!',
                'CNPJ já cadastrado',
                'error'
              );
              this.showCnpjError = true;
              this.cnpjvalid = true;
            } else {
              this.cnpjvalid = false;
            }
            this.showCnpjError = false;
          }
        );
      }
    });
    return false;
  }

  confimaemail(): boolean {
    this.servico.getOne(this.cliId).subscribe(e => {
      if (e.email === this.c.email) {
        return false;
      } else {
        this.servico.emailExist(this.c.email).subscribe(r => {
            if (r) {
              swal(
                'Erro!',
                'Email já cadastrado',
                'error'
              );
              this.showEmailError = true;
              this.emailvalid = true;
            } else {
              this.emailvalid = false;
            }
            this.showEmailError = false;
          }
        );
      }
    });
    return false;
  }

  editar() {
    this.servico.update(this.cliId, this.c).subscribe(() => {
        this.router.navigate(['/login']);
      }, () => {
        swal('Erro!',
          'Falha no banco de dados\n Tente mais tarde',
          'error');
      }
    );
  }

  back() {
    history.back();
  }
}
