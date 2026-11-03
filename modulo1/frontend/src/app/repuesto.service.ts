import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Repuesto } from './repuesto';


@Injectable({ providedIn: 'root'})
export class RepuestoService{
  private readonly http = inject(HttpClient);
  private readonly apiUrl = 'http://localhost:8080/api/repuestos';

  listar(): Observable<Repuesto[]>{
    return this.http.get<Repuesto[]>(this.apiUrl);
  }
}
