import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { PesquisaCreditosComponent } from "./pesquisa-creditos/pesquisa-creditos.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, PesquisaCreditosComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'app';
}
