package pe.com.pevisa.repuesto.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.pevisa.repuesto.dto.RepuestoRequest;
import pe.com.pevisa.repuesto.dto.RepuestoResponse;
import pe.com.pevisa.repuesto.service.RepuestoService;

import java.util.List;

/**
 * Capa de presentación — transforma HTTP en llamadas al Service.
 *
 * Reglas de la capa:
 *  - Solo habla HTTP: recibe requests, devuelve ResponseEntity.
 *  - No contiene lógica de negocio (ni un if de reglas de dominio).
 *  - Delega todo al Service.
 *
 * @RestController = @Controller + @ResponseBody
 *   Cada método devuelve el objeto directamente serializado a JSON.
 *
 * @RequestMapping("/api/v1/repuestos")
 *   Versionamos la URL desde el inicio. Cuando necesitemos romper el
 *   contrato, creamos /api/v2/… sin romper a los clientes del v1.
 */
@RestController
@RequestMapping("/api/v1/repuestos")
@RequiredArgsConstructor
public class RepuestoController {

    private final RepuestoService servicio;

    /**
     * GET /api/v1/repuestos
     * GET /api/v1/repuestos?division=BOSCH
     *
     * El parámetro opcional "division" filtra sin crear un endpoint separado.
     */
    @GetMapping
    public List<RepuestoResponse> listar(
            @RequestParam(required = false) String division) {

        if (division != null && !division.isBlank()) {
            return servicio.buscarPorDivision(division);
        }
        return servicio.listar();
    }

    /**
     * GET /api/v1/repuestos/{id}
     */
    @GetMapping("/{id}")
    public RepuestoResponse buscarPorId(@PathVariable Long id) {
        return servicio.buscarPorId(id);
    }

    /**
     * POST /api/v1/repuestos
     * Body: RepuestoRequest (validado con @Valid)
     * Responde 201 Created con el recurso creado.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RepuestoResponse crear(@Valid @RequestBody RepuestoRequest request) {
        return servicio.crear(request);
    }

    /**
     * PUT /api/v1/repuestos/{id}
     * Reemplazo completo del recurso (todos los campos requeridos).
     */
    @PutMapping("/{id}")
    public RepuestoResponse actualizar(
            @PathVariable Long id,
            @Valid @RequestBody RepuestoRequest request) {
        return servicio.actualizar(id, request);
    }

    /**
     * DELETE /api/v1/repuestos/{id}
     * Responde 204 No Content (sin body).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
