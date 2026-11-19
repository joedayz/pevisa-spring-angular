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
 * TODO 26: Anotar la clase con @RestController y @RequestMapping("/api/v1/repuestos")
 *
 * TODO 27: Completar cada endpoint:
 *   GET    /api/v1/repuestos              → listar (con ?division= opcional)
 *   GET    /api/v1/repuestos/{id}         → buscarPorId
 *   POST   /api/v1/repuestos              → crear (201 Created)
 *   PUT    /api/v1/repuestos/{id}         → actualizar
 *   DELETE /api/v1/repuestos/{id}         → eliminar (204 No Content)
 *
 * TODO 28: Agregar @Valid en el @RequestBody de crear y actualizar.
 *          ¿Qué pasa si no lo pones?
 */
@RequiredArgsConstructor
public class RepuestoController {

    private final RepuestoService servicio;

    // TODO 27a: GET listar con división opcional
    public List<RepuestoResponse> listar(String division) {
        return List.of();
    }

    // TODO 27b: GET por id
    public RepuestoResponse buscarPorId(Long id) {
        return null;
    }

    // TODO 27c: POST crear — responder 201
    public RepuestoResponse crear(RepuestoRequest request) {
        return null;
    }

    // TODO 27d: PUT actualizar
    public RepuestoResponse actualizar(Long id, RepuestoRequest request) {
        return null;
    }

    // TODO 27e: DELETE — responder 204 No Content
    public ResponseEntity<Void> eliminar(Long id) {
        return ResponseEntity.noContent().build();
    }
}
