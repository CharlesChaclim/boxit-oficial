import {Component, OnInit} from '@angular/core';
import {FuncionarioService} from '../funcionario.service';
import {Funcionario} from '../../core/model';
import {ActivatedRoute, Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';
import {ErrorHandleService} from '../../core/error-handle.service';
import {MyMaskUtil} from '../../shared/mask/my-mask.util';
import {filter} from 'rxjs/operators';

@Component({
  selector: 'app-view-funcionario',
  templateUrl: './view-funcionario.component.html',
  styleUrls: ['./view-funcionario.component.scss']
})
export class ViewFuncionarioComponent implements OnInit {
  title = 'Novo Funcionário';
  buttonText = 'Cadastrar';
  f = new Funcionario();
  mail: string;
  username: string;
  cpf: string;
  mailValid = false;
  usernameValid = false;
  cpfValid = false;
  edit = false;
  view = false;
  public cpfMask = MyMaskUtil.CPF_MASK_GENERATOR;
  public phoneMask = MyMaskUtil.DYNAMIC_PHONE_MASK_GENERATOR;
  confirmSenha = '';
  passwordValid = false;
  funcionarioId: string;
  changePass = false;

  constructor(
    private funcionarioService: FuncionarioService,
    private router: Router,
    private route: ActivatedRoute,
    private toastr: ToastrService,
    private err: ErrorHandleService
  ) { }

  ngOnInit() {
    this.funcionarioId = this.route.snapshot.params['id'];
    this.edit = this.route.snapshot.params['edit'];
    if (this.funcionarioId && !this.edit) {
      this.view = true;
    }
    if (this.funcionarioId !== undefined) {
      this.funcionarioService.getOne(this.funcionarioId).subscribe(
        r => {
          this.f = r;
          this.mail = r.email;
          this.username = r.username;
          this.cpf = r.cpf;
          if (this.view) {
            this.title = 'Detalhes de ' + this.f.nome;
          } else if (this.edit) {
            this.title = 'Editar ' + this.f.nome;
            this.buttonText = 'Atualizar';
          }
      });
    }
  }

  create() {
    this.funcionarioService.create(this.f).subscribe(
      r => {
        this.router.navigate(['/funcionario']);
        this.toastr.success('Funcionário ' + this.f.nome + ' cadastrado com sucesso!');
      }, e => {
        console.log(e);
      }
    );
  }

  confirmaEmail(): boolean {
    if (this.mail === this.f.email) {
      return false;
    } else {
      this.funcionarioService.emailExist(this.f.email).subscribe(
        r => {
          if (r) {
            this.mailValid = true;
            return true;
          } else {
            this.mailValid = false;
            return false;
          }
        }
      );
    }
  }

  confirmaUsername(): boolean {
    if (this.username === this.f.username) {
      return false;
    } else {
      this.funcionarioService.usernameExist(this.f.username).subscribe(
        r => {
          if (r) {
            this.usernameValid = true;
            return true;
          } else {
            this.usernameValid = false;
            return false;
          }
        }
      );
    }
  }

  confirmaCpf(): boolean {
    if (this.cpf === this.f.cpf) {
      return false;
    } else {
      this.funcionarioService.cpfExist(this.f.cpf).subscribe(
        r => {
          if (r) {
            this.cpfValid = true;
            return true;
          } else {
            this.cpfValid = false;
            return false;
          }
        }
      );
    }
  }

  clear() {
    delete this.f.password;
    delete this.confirmSenha;
  }

  update() {
    this.funcionarioService.update(this.f).subscribe(
      r => {
        this.router.navigate(['/funcionario']);
        this.toastr.success('Funcionário ' + this.f.nome + ' atualizado com sucesso!');
      }
    );
  }

  confirmasenha(): boolean {
    this.passwordValid = this.f.password !== this.confirmSenha;
    return this.passwordValid;
  }

  back() {
    history.back();
  }
}
