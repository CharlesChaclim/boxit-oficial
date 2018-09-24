import {Component, OnInit} from '@angular/core';
import {MyMaskUtil} from '../../shared/mask/my-mask.util';
import {ClienteEdit} from '../../core/model';
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
  c = new ClienteEdit();
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
        this.populate();
      } else {
        this.router.navigate(['/cliente']);
      }
    });
  }

  populate() {
    this.update = true;
    this.servico.getOne(this.cliId).subscribe(
      s => {
        this.c = s;
      }, () => {
        swal(
          'Erro!',
          'Erro no servidor!',
          'error'
        );
      }
    );
  }

  buscaEndereco() {
    this.correiosService.getEndereco(this.c.endereco.cep).subscribe(
      r => {
        this.c.endereco.cidade = r.cidade;
        this.c.endereco.endereco = r.endereco;
        this.c.endereco.bairro = r.bairro;
        this.c.endereco.estado = r.uf;
      }
    );
  }

  confirmasenha(): boolean {
    if (!this.update) {
      return this.c.password !== this.csenha;
    } else {
      return true;
    }
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
