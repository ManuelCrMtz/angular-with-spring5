import { Component, OnInit } from '@angular/core';
import {Cliente} from './cliente';
import {ClientesService} from './clientes.service';

@Component({
  selector: 'app-clientes',
  templateUrl: './clientes.component.html',
  styleUrls: ['./clientes.component.css']
})
export class ClientesComponent implements OnInit {

  public clientes!: Cliente[];

  constructor( private clientesService: ClientesService) { }

  ngOnInit(): void {
    this.clientesService
      .getClientes().subscribe(clientes => this.clientes = clientes);
  }

}
