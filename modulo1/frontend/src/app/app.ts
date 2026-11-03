import { Component, inject, signal } from '@angular/core';
import { Repuesto } from './repuesto';
import { RepuestoService } from './repuesto.service';
import { CurrencyPipe } from '@angular/common';


@Component({
  selector: 'app-root',
  styleUrl: './app.css',
  templateUrl: './app.html',
  imports: [CurrencyPipe],
})
export class App {
  private readonly repuestoService = inject(RepuestoService);

  protected readonly repuestos = signal<Repuesto[]>([]);
  protected readonly cargando = signal(true);
  protected readonly error = signal<string | null>(null);

  constructor() {
    this.repuestoService.listar().subscribe({
      next: (repuestos) => {
        this.repuestos.set(repuestos);
        this.cargando.set(false);
      },
      error: () => {
        this.error.set(
          'No se pudo cargar el catálogo. Revisa que el backend esté en http://localhost:8080.',
        );
        this.cargando.set(false);
      },
    });
  }
}
