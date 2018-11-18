import {Component, OnInit} from '@angular/core';
import {MyMaskUtil} from '../../shared/mask/my-mask.util';
import {ClienteEdit} from '../../core/model';
import {CorreiosService} from '../../shared/correios.service';
import {ClienteService} from '../cliente.service';
import {ActivatedRoute, Router} from '@angular/router';
import swal from 'sweetalert2';
import {ErrorHandleService} from '../../core/error-handle.service';
import {AuthService} from '../../auth/auth.service';

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html'
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
  edit = false;
  title: string;
  podeEdit = false;

  constructor(
    private correiosService: CorreiosService,
    private servico: ClienteService,
    private router: Router,
    private errHandle: ErrorHandleService,
    private activatedRoute: ActivatedRoute,
    private auth: AuthService
  ) {
    const user = this.auth.jwtPayload.user_name;
    console.log(user.length);
    if (user.length > 17) {
      this.podeEdit = true;
    } else {
      this.podeEdit = false;
    }
  }

  ngOnInit() {
    this.edit = this.activatedRoute.snapshot.params['edit'];
    this.activatedRoute.paramMap.subscribe(params => {
      this.cliId = params.get('id');
      if (this.cliId) {
        this.populate();
      } else {
        this.router.navigate(['/cliente']);
      }
    });
    if (!this.edit) {
      this.edit = false;
    }
  }

  populate() {
    this.servico.getOne(this.cliId).subscribe(
      s => {
        this.c = s;
        this.c.password = null;
        if (this.edit) {
          this.title = 'Editar o cliente ' + this.c.nomeFantasia;
        } else {
          this.title = 'Detalhes do cliente ' + this.c.nomeFantasia;
        }
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
        if (r.cidade !== '') {
          this.c.endereco.cidade = r.cidade;
        }
        if (r.endereco !== '') {
          this.c.endereco.endereco = r.endereco;
        }
        if (r.bairro !== '') {
          this.c.endereco.bairro = r.bairro;
        }
        if (r.uf !== '') {
          this.c.endereco.estado = r.uf;
        }
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
        this.router.navigate(['/cliente']);
      }, () => {
        swal('Erro!',
          'CNPJ ou CPF Inválido',
          'error');
      }
    );
  }
  back() {
    history.back();
  }
  help() {
    if (!this.edit) {
      swal({
          title: 'Ajuda',
          html: 'Não é possível editar o conteúdo desta página pois ela é só para visualização e não para edição',
          type: 'info'
        }
      );
    } else {
      swal({
          title: 'Ajuda',
          html: 'O nome fantasia é o nome da empresa.' +
            '<br> <br>' +
            'O logadouro é o nome da rua/avenida/rodovia/estrada que a empresa é sediada.' +
            '<br> <br>' +
            'Os campos com * ao seu lado são obrigatórios.' +
            '<br> <br>' +
            'Caso deixe vazio o campo senha a antiga permanecerá.',
          type: 'info'
        }
      );
    }
  }
}
