import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { ToastrService } from 'ngx-toastr';
import { Cliente } from '../util/domains/cliente.domain';
import { ClienteService } from '../util/service/cliente.service';

@Component({
  selector: 'app-cliente',
  templateUrl: './cliente.component.html',
  styleUrls: ['./cliente.component.css']
})
export class ClienteComponent implements OnInit {

  listCliente: Cliente[];
  animal: string;
  name: string;
  displayedColumns: string[] = ['nome', 'email', 'acao'];

  constructor(private clienteService: ClienteService) { }

  ngOnInit() {
    this.listCliente = [];
    this.clienteService.getAll().subscribe(list => {
      this.listCliente = list;
    });
  }
}
