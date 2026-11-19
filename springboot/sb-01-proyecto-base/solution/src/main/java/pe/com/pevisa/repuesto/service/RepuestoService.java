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
 * Capa de servicio — aquí vive la lógica de negocio.
 *
 * Reglas de la capa:
 *  - No sabe nada de HTTP (no importa HttpServletRequest, no devuelve ResponseEntity).
 *  - No sabe cómo se persisten los datos (habla con la interfaz, no con la implementación).
 *  - Sí puede lanzar excepciones de negocio (RepuestoNotFoundException, etc.).
 *
 * @Slf4j genera el campo "log" para escribir mensajes sin declararlo a mano.
 * @RequiredArgsConstructor genera el constructor con los campos final
 * — es la forma preferida de inyección de dependencias en Spring.
 *
 * PRÓXIMAS SESIONES:
 *  SB-02: agregar @Transactional aquí cuando haya BD real.
 *  SB-05: agregar @Cacheable en listar() y buscarPorId().
 *  SB-10: AOP interceptará cada método para registrar quién lo llamó y cuánto tardó.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RepuestoService {

    private final RepuestoRepository repositorio;

    public List<RepuestoResponse> listar() {
        log.info("Listando todos los repuestos");
        return repositorio.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public RepuestoResponse buscarPorId(Long id) {
        log.info("Buscando repuesto id={}", id);
        return repositorio.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new RepuestoNotFoundException(id));
    }

    public List<RepuestoResponse> buscarPorDivision(String division) {
        log.info("Buscando repuestos de división={}", division);
        return repositorio.findByDivision(division).stream()
                .map(this::toResponse)
                .toList();
    }

    public RepuestoResponse crear(RepuestoRequest request) {
        log.info("Creando repuesto codigo={}", request.getCodigo());

        // Regla de negocio: no permitir códigos duplicados
        repositorio.findByCodigo(request.getCodigo()).ifPresent(existente -> {
            throw new IllegalArgumentException(
                    "Ya existe un repuesto con el código: " + request.getCodigo()
            );
        });

        Repuesto nuevo = toModel(request);
        Repuesto guardado = repositorio.save(nuevo);
        log.info("Repuesto creado id={}", guardado.getId());
        return toResponse(guardado);
    }

    public RepuestoResponse actualizar(Long id, RepuestoRequest request) {
        log.info("Actualizando repuesto id={}", id);

        Repuesto existente = repositorio.findById(id)
                .orElseThrow(() -> new RepuestoNotFoundException(id));

        existente.setCodigo(request.getCodigo());
        existente.setDescripcion(request.getDescripcion());
        existente.setDivision(request.getDivision());
        existente.setPrecio(request.getPrecio());
        existente.setStock(request.getStock());

        return toResponse(repositorio.save(existente));
    }

    public void eliminar(Long id) {
        log.info("Eliminando repuesto id={}", id);
        if (!repositorio.existsById(id)) {
            throw new RepuestoNotFoundException(id);
        }
        repositorio.deleteById(id);
    }

    // ── Mapeo manual DTO ↔ Modelo ──────────────────────────────────────────
    // En SB-06 reemplazamos esto con MapStruct (generación en tiempo de compilación).

    private RepuestoResponse toResponse(Repuesto r) {
        return RepuestoResponse.builder()
                .id(r.getId())
                .codigo(r.getCodigo())
                .descripcion(r.getDescripcion())
                .division(r.getDivision())
                .precio(r.getPrecio())
                .stock(r.getStock())
                .estadoStock(calcularEstado(r.getStock()))
                .build();
    }

    private Repuesto toModel(RepuestoRequest req) {
        return Repuesto.builder()
                .codigo(req.getCodigo())
                .descripcion(req.getDescripcion())
                .division(req.getDivision())
                .precio(req.getPrecio())
                .stock(req.getStock())
                .build();
    }

    /**
     * Lógica de negocio pura: calcula el estado visual del stock.
     * No pertenece ni al controller ni al DTO — pertenece aquí.
     */
    private String calcularEstado(int stock) {
        if (stock == 0)   return "AGOTADO";
        if (stock < 10)   return "BAJO";
        if (stock < 50)   return "NORMAL";
        return "ALTO";
    }
}
