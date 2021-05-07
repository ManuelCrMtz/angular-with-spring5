import { Component, OnInit } from '@angular/core';
import {Cliente} from './cliente';
import {Router, ActivatedRoute} from '@angular/router';
import {ClientesService} from './clientes.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html'
})
export class FormComponent implements OnInit {

  public cliente: Cliente = new Cliente();
  public titulo = 'Crear Cliente';

  constructor(private clienteService: ClientesService,
              private router: Router,
              private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.cargarCliente();
  }

  cargarCliente(): void{
    this.activatedRoute.params.subscribe(params => {
      const id = params.id;
      if (id){
        this.clienteService.getCliente(id).subscribe( (cliente) => this.cliente = cliente);
      }
    });
  }

  create(): void {
    this.clienteService.create(this.cliente)
      .subscribe(cliente => {
          this.router.navigate(['/clientes']);
          // swal.fire('Nuevo cliente', `Cliente ${cliente.nombre} creado con éxito!`, 'success');
          Swal.fire('Nuevo cliente', `Cliente ${cliente.nombre} creado con éxito!`, 'success');
        }
      );
  }

  update(): void {
    this.clienteService.update(this.cliente)
      .subscribe( cliente => {
          this.router.navigate(['/clientes']);
          Swal.fire('Cliente Actualizado', `Cliente ${cliente.nombre} actualizado con éxito!`, 'success');
        }
      );
  }

}
