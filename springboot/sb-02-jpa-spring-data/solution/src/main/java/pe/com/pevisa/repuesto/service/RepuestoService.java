package pe.com.pevisa.repuesto.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.pevisa.repuesto.dto.PageResponse;
import pe.com.pevisa.repuesto.dto.RepuestoRequest;
import pe.com.pevisa.repuesto.dto.RepuestoResponse;
import pe.com.pevisa.repuesto.exception.RepuestoNotFoundException;
import pe.com.pevisa.repuesto.model.Repuesto;
import pe.com.pevisa.repuesto.repository.RepuestoRepository;

import java.util.List;

/**
 * CAMBIOS respecto a SB-01:
 *
 * 1. @Transactional — ahora hay una BD real que puede fallar.
 *    - En métodos de lectura: @Transactional(readOnly = true)
 *      Hibernate no rastrea cambios → más rápido y menos memoria.
 *    - En métodos de escritura: @Transactional (readOnly = false por defecto)
 *      Si lanza excepción → rollback automático. Todo o nada.
 *
 * 2. existsByCodigo — más eficiente que findByCodigo (no trae la entidad completa).
 *
 * 3. Paginación con PageRequest → Page<Repuesto> → PageResponse<RepuestoResponse>.
 *
 * 4. toResponse() ahora mapea también createdAt / updatedAt.
 *
 * LO QUE NO CAMBIÓ: la firma pública de todos los métodos.
 * El Controller no sabe que ahora hay JPA — ni necesita saberlo.
 *
 * IMPORTANTE — problema N+1:
 * Si Repuesto tuviera una relación @OneToMany (ej: List<Imagen>)
 * y llamaras findAll(), Hibernate haría 1 SELECT para los repuestos
 * + N SELECTs para las imágenes de cada uno = N+1 queries.
 * La solución: JOIN FETCH en la query o @EntityGraph.
 * Lo veremos en detalle cuando agreguemos relaciones en esta misma sesión.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)   // ← por defecto todas las operaciones son lectura
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
        return repositorio.findByDivisionIgnoreCase(division).stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Paginación — el Controller pasa página y tamaño, el Service
     * construye el Pageable y devuelve un PageResponse con metadata.
     */
    public PageResponse<RepuestoResponse> listarPaginado(int pagina, int tamano, String ordenarPor) {
        log.info("Listando paginado: pagina={}, tamano={}, ordenarPor={}", pagina, tamano, ordenarPor);

        PageRequest pageRequest = PageRequest.of(pagina, tamano, Sort.by(ordenarPor));
        Page<Repuesto> page = repositorio.findAll(pageRequest);

        return PageResponse.<RepuestoResponse>builder()
                .contenido(page.getContent().stream().map(this::toResponse).toList())
                .paginaActual(page.getNumber())
                .totalPaginas(page.getTotalPages())
                .totalElementos(page.getTotalElements())
                .tamanioPagina(page.getSize())
                .ultima(page.isLast())
                .build();
    }

    public List<RepuestoResponse> buscarConStockBajo(int umbral) {
        return repositorio.findRepuestosConStockBajo(umbral).stream()
                .map(this::toResponse)
                .toList();
    }

    public List<RepuestoResponse> buscar(String termino) {
        return repositorio.buscarEnDescripcion(termino).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional   // ← sobreescribe readOnly = true: esta operación escribe
    public RepuestoResponse crear(RepuestoRequest request) {
        log.info("Creando repuesto codigo={}", request.getCodigo());

        if (repositorio.existsByCodigo(request.getCodigo())) {
            throw new IllegalArgumentException(
                    "Ya existe un repuesto con el código: " + request.getCodigo());
        }

        Repuesto nuevo = toModel(request);
        Repuesto guardado = repositorio.save(nuevo);
        log.info("Repuesto creado id={}", guardado.getId());
        return toResponse(guardado);
    }

    @Transactional
    public RepuestoResponse actualizar(Long id, RepuestoRequest request) {
        log.info("Actualizando repuesto id={}", id);

        Repuesto existente = repositorio.findById(id)
                .orElseThrow(() -> new RepuestoNotFoundException(id));

        /*
         * Patrón de actualización con JPA:
         * No hace falta llamar save() si la entidad ya está "managed"
         * (dentro de la transacción). Hibernate detecta los cambios
         * automáticamente al hacer commit → dirty checking.
         *
         * Llamamos save() de todas formas para ser explícitos y
         * recibir la entidad actualizada con los timestamps.
         */
        existente.setCodigo(request.getCodigo());
        existente.setDescripcion(request.getDescripcion());
        existente.setDivision(request.getDivision());
        existente.setPrecio(request.getPrecio());
        existente.setStock(request.getStock());

        return toResponse(repositorio.save(existente));
    }

    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando repuesto id={}", id);
        if (!repositorio.existsById(id)) {
            throw new RepuestoNotFoundException(id);
        }
        repositorio.deleteById(id);
    }

    // ── Mapeo DTO ↔ Entidad ────────────────────────────────────────────────
    // En SB-06 esto lo reemplaza MapStruct.

    private RepuestoResponse toResponse(Repuesto r) {
        return RepuestoResponse.builder()
                .id(r.getId())
                .codigo(r.getCodigo())
                .descripcion(r.getDescripcion())
                .division(r.getDivision())
                .precio(r.getPrecio())
                .stock(r.getStock())
                .estadoStock(calcularEstado(r.getStock()))
                .createdAt(r.getCreatedAt())
                .updatedAt(r.getUpdatedAt())
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

    private String calcularEstado(int stock) {
        if (stock == 0)  return "AGOTADO";
        if (stock < 10)  return "BAJO";
        if (stock < 50)  return "NORMAL";
        return "ALTO";
    }
}
