import {Component, OnInit} from '@angular/core';
import {ErrorHandleService} from '../../core/error-handle.service';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthService} from '../auth.service';
import {ToastrService} from 'ngx-toastr';
import {NgForm} from '@angular/forms';

@Component({
  selector: 'app-confirm',
  templateUrl: './confirm.component.html'
})
export class ConfirmComponent implements OnInit {
  loading = false;

  constructor(
    private errHandle: ErrorHandleService,
    private auth: AuthService,
    private router: Router,
    private route: ActivatedRoute,
    private toast: ToastrService
  ) {
    this.route.queryParamMap.subscribe(
      params => {
        const code = params.get('code');
        if (code) {
          this.confirmarCadastro(null, code);
        }
      }
    );
  }

  ngOnInit() {
  }

  confirmarCadastro(f: NgForm, codigo: string) {
    this.loading = true;
    this.auth.confirmar(codigo)
      .then(() => {
        this.router.navigate(['/']);
        this.toast.success('Sua conta foi ativada com sucesso!');
      }).catch(err => {
      this.loading = false;
      if (f) {
        f.reset();
      }
      this.errHandle.handle(err);
    });
  }
}
