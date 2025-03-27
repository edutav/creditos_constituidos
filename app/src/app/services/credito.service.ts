import { Injectable } from "@angular/core";
import { environment } from "../../environments/environment";
import { HttpClient } from "@angular/common/http";
import { catchError, Observable } from "rxjs";
import { Credito } from "../models/credito.model";

@Injectable({
    providedIn: 'root'
})
export class CreditoService { 
    private apiURL = `${environment.api}/api/creditos`;

    constructor(private http: HttpClient) { }

    getCreditosPorNfse(numeroNfse: string): Observable<Credito[]> {
        return this.http.get<Credito[]>(`${this.apiURL}/${numeroNfse}`).pipe(
            catchError((error) => {
                console.error('Erro ao buscar créditos por NFS-e:', error);
                throw error; // Propaga o erro para ser tratado no componente
            })
        );
    }

    buscarCreditoPorNumero(numeroCredito: string): Observable<Credito> {
        return this.http.get<Credito>(`${this.apiURL}/credito/${numeroCredito}`).pipe(
            catchError((error) => {
                console.error('Erro ao buscar detalhes do crédito:', error);
                throw error; // Propaga o erro para ser tratado no componente
            })
        );
    }
}