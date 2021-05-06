import { Component } from '@angular/core';

@Component({
  selector: 'app-directiva',
  templateUrl: './directiva.component.html',
  styleUrls: ['./directiva.component.css']
})
export class DirectivaComponent {

  public listadoCurso: string[] = ['TypeScript', 'Javascript', 'Java', 'C#', 'PHP'];
  public habilitar = true;

  constructor() { }

}
