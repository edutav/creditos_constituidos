import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CreditoService } from '../services/credito.service';
import { Credito } from '../models/credito.model';

@Component({
  selector: 'app-pesquisa-creditos',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './pesquisa-creditos.component.html',
  styleUrl: './pesquisa-creditos.component.scss'
})
export class PesquisaCreditosComponent {
  numeroNfse: string = '';
  numeroCredito: string = '';
  creditosNfse: Credito[] = [];
  creditoDetalhe: Credito | null = null;
  erroNfse: string = '';
  erroCredito: string = '';
  anoagora: Date = new Date();

  constructor(private creditoSerivice: CreditoService) { }

  buscarCreditosPorNfse() {
    this.numeroCredito = '';
    this.erroNfse = '';
    this.creditosNfse = [];
    this.creditoDetalhe = null;
    
    this.creditoSerivice.getCreditosPorNfse(this.numeroNfse).subscribe({
      next: (data: Credito[]) => {
        this.creditosNfse = data;
      },
      error: (error) => {
        this.erroNfse = 'Erro ao buscar créditos por NFS-e.';
      }
    });
  }

  buscarCreditoPorNumero() {
    this.numeroNfse = '';
    this.erroCredito = '';
    this.creditoDetalhe = null;
    this.creditosNfse = [];
    this.creditoSerivice.buscarCreditoPorNumero(this.numeroCredito).subscribe({
      next: (data: Credito) => {
        this.creditoDetalhe = data;
      },
      error: (error) => {
        this.erroCredito = 'Erro ao buscar detalhes do crédito.';
      }
    });
  }
}
