import { Component, inject, signal } from '@angular/core';
import { Repuesto } from './repuesto';
import { RepuestoService } from './repuesto.service';
import { NgForOf } from '@angular/common';


@Component({
  selector: 'app-root',
  styleUrl: './app.css',
  templateUrl: './app.html',
})
export class App {
  private readonly repuestoService = inject(RepuestoService);

  protected readonly repuestos = signal<Repuesto[]>([]);

  constructor() {
    this.repuestoService.listar().subscribe({
      next: (repuestos) => this.repuestos.set(repuestos),
      error: (err) => console.error(err),
    });
  }
}
