package pe.com.pevisa.repuesto.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.com.pevisa.repuesto.dto.RepuestoRequest;
import pe.com.pevisa.repuesto.dto.RepuestoResponse;
import pe.com.pevisa.repuesto.exception.RepuestoNotFoundException;
import pe.com.pevisa.repuesto.model.Repuesto;
import pe.com.pevisa.repuesto.repository.RepuestoRepository;

import java.util.List;

/**
 * TODO 22: Completar listar(), buscarPorId(), buscarPorDivision(), crear(), actualizar(), eliminar()
 *
 * TODO 23: En crear(), agregar la regla de negocio: no permitir códigos duplicados.
 *
 * TODO 24: Implementar calcularEstado(int stock):
 *   stock == 0  → "AGOTADO"
 *   stock < 10  → "BAJO"
 *   stock < 50  → "NORMAL"
 *   en otro caso → "ALTO"
 *
 * TODO 25: Implementar toResponse() y toModel() para convertir entre capas.
 *          (En SB-06 reemplazaremos esto con MapStruct)
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RepuestoService {

    private final RepuestoRepository repositorio;

    public List<RepuestoResponse> listar() {
        // TODO 22a
        return List.of();
    }

    public RepuestoResponse buscarPorId(Long id) {
        // TODO 22b: usar orElseThrow con RepuestoNotFoundException
        return null;
    }

    public List<RepuestoResponse> buscarPorDivision(String division) {
        // TODO 22c
        return List.of();
    }

    public RepuestoResponse crear(RepuestoRequest request) {
        // TODO 23: verificar código duplicado antes de guardar
        // TODO 22d: convertir request → modelo, guardar, convertir → response
        return null;
    }

    public RepuestoResponse actualizar(Long id, RepuestoRequest request) {
        // TODO 22e
        return null;
    }

    public void eliminar(Long id) {
        // TODO 22f
    }

    private RepuestoResponse toResponse(Repuesto r) {
        // TODO 25a
        return null;
    }

    private Repuesto toModel(RepuestoRequest req) {
        // TODO 25b
        return null;
    }

    private String calcularEstado(int stock) {
        // TODO 24
        return null;
    }
}
