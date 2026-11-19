package pe.com.pevisa.repuesto.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.pevisa.repuesto.dto.PageResponse;
import pe.com.pevisa.repuesto.dto.RepuestoRequest;
import pe.com.pevisa.repuesto.dto.RepuestoResponse;
import pe.com.pevisa.repuesto.service.RepuestoService;

import java.util.List;

/**
 * CAMBIOS respecto a SB-01:
 *   + GET /api/v1/repuestos/paginado  → paginación
 *   + GET /api/v1/repuestos/stock-bajo?umbral=10
 *   + GET /api/v1/repuestos/buscar?q=toyota
 *
 * El resto del Controller no cambió — ni la clase base ni los endpoints existentes.
 */
@RestController
@RequestMapping("/api/v1/repuestos")
@RequiredArgsConstructor
public class RepuestoController {

    private final RepuestoService servicio;

    @GetMapping
    public List<RepuestoResponse> listar(
            @RequestParam(required = false) String division) {
        if (division != null && !division.isBlank()) {
            return servicio.buscarPorDivision(division);
        }
        return servicio.listar();
    }

    /**
     * GET /api/v1/repuestos/paginado?pagina=0&tamano=10&ordenarPor=codigo
     *
     * Siempre paginar en producción. Un SELECT * sin LIMIT en una tabla
     * de 500.000 repuestos trae la BD de rodillas.
     */
    @GetMapping("/paginado")
    public PageResponse<RepuestoResponse> listarPaginado(
            @RequestParam(defaultValue = "0")    int pagina,
            @RequestParam(defaultValue = "10")   int tamano,
            @RequestParam(defaultValue = "codigo") String ordenarPor) {
        return servicio.listarPaginado(pagina, tamano, ordenarPor);
    }

    /**
     * GET /api/v1/repuestos/stock-bajo?umbral=10
     * Lista repuestos que necesitan reabastecerse.
     */
    @GetMapping("/stock-bajo")
    public List<RepuestoResponse> stockBajo(
            @RequestParam(defaultValue = "10") int umbral) {
        return servicio.buscarConStockBajo(umbral);
    }

    /**
     * GET /api/v1/repuestos/buscar?q=toyota
     * Búsqueda por descripción.
     */
    @GetMapping("/buscar")
    public List<RepuestoResponse> buscar(@RequestParam String q) {
        return servicio.buscar(q);
    }

    @GetMapping("/{id}")
    public RepuestoResponse buscarPorId(@PathVariable Long id) {
        return servicio.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RepuestoResponse crear(@Valid @RequestBody RepuestoRequest request) {
        return servicio.crear(request);
    }

    @PutMapping("/{id}")
    public RepuestoResponse actualizar(
            @PathVariable Long id,
            @Valid @RequestBody RepuestoRequest request) {
        return servicio.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
