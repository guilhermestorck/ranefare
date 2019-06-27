import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Cidade } from '../../util/domains/cidade.domain';
import { Cliente } from '../../util/domains/cliente.domain';
import { CidadeService } from '../../util/service/cidade.service';
import { ClienteService } from '../../util/service/cliente.service';
import { ValidationCPF } from '../../util/validations/validation-cpf';

@Component({
  selector: 'cadastro-cliente',
  templateUrl: './cadastro-cliente.component.html',
  styleUrls: ['./cadastro-cliente.component.css'],
  providers:[ClienteService, CidadeService]
})
export class CadastroClienteComponent implements OnInit {

  emailFormControl = new FormControl('', [
    Validators.required,
    Validators.email,
  ]);

  cpfFormControl = new FormControl('', [
    Validators.required,
  ]);

  cidades: Cidade[] = [];
  cliente: Cliente = new Cliente();
  email = new FormControl('', [Validators.required, Validators.email]);
  isEdited: Boolean = false;
  clienteId: any;
  isValidCPF: boolean;

  constructor(private clienteService: ClienteService,
    private toastrService: ToastrService,
    private cidadeService: CidadeService,
    private router: Router) { }

  ngOnInit() {
    this.verificaRota();
    this.getCidades();
  }

  verificaRota(): void {
    console.log(this.router.url);
    this.clienteId = this.router.url.split('/')[3];
    this.getCliente()
  }

  getCliente(): void {
    this.clienteService.getOne(this.clienteId).subscribe(cliente => {
      this.cliente = cliente;
      this.verificaTipoPessoa(cliente);
      this.isEdited = true;
    });
  }

  verificaTipoPessoa(cliente): void {
    if (cliente.tipoCliente == 'PESSOA_FISICA') {
      this.cliente.tipo = 1;
    } else {
      this.cliente.tipo = 2;
    }
  }
  getErrorMessage() {
    return this.email.hasError('required') ? 'You must enter a value' :
      this.email.hasError('email') ? 'Not a valid email' :
        '';
  }
  getCidades(): void {
    this.cidadeService.getCidades(31).subscribe(res => {
      this.cidades = res;
    });
  }

  salvar() {
    this.cliente.senha = "123";
    this.clienteService.save(this.cliente).subscribe(cliente => {
      this.toastrService.success("Cliente salvo com sucesso!");
      this.router.navigate(['/cliente']);
    }, err => {
      this.toastrService.error("Erro ao salvar cliente!");
    });
  }

  atualizar() {
    this.cliente.id = this.clienteId;
    this.clienteService.save(this.cliente).subscribe(cliente => {
      this.toastrService.success("Cliente atualizado com sucesso!");
      this.router.navigate(['/cliente']);
    }, err => {
      this.toastrService.error("Erro ao salvar cliente!");
    });
  }

  validateCPF() {
    this.isValidCPF = ValidationCPF.cpf(this.cliente.cpfCnpj);
  }
}
