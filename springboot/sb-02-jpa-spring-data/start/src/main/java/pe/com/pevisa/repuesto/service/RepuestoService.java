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
 * TODO 12: Agregar @Transactional(readOnly = true) a nivel de clase.
 *   Pregunta: ¿qué optimización hace Hibernate cuando readOnly = true?
 *
 * TODO 13: Agregar @Transactional (sobreescribir readOnly) en crear(), actualizar(), eliminar().
 *   Pregunta: si crear() lanza una excepción a mitad de ejecución,
 *             ¿qué pasa con los cambios ya hechos en la BD?
 *
 * TODO 14: En crear(), cambiar findByCodigo().ifPresent() por existsByCodigo().
 *   Pregunta: ¿por qué existsByCodigo() es más eficiente?
 *
 * TODO 15: Implementar listarPaginado(int pagina, int tamano, String ordenarPor):
 *   - Construir PageRequest.of(pagina, tamano, Sort.by(ordenarPor))
 *   - Llamar repositorio.findAll(pageRequest) → Page<Repuesto>
 *   - Convertir a PageResponse<RepuestoResponse> con toda la metadata
 *
 * TODO 16: Actualizar toResponse() para incluir createdAt y updatedAt.
 */
@Slf4j
@Service
@RequiredArgsConstructor
// TODO 12: @Transactional(readOnly = true)
public class RepuestoService {

    private final RepuestoRepository repositorio;

    public List<RepuestoResponse> listar() {
        return repositorio.findAll().stream().map(this::toResponse).toList();
    }

    public RepuestoResponse buscarPorId(Long id) {
        return repositorio.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new RepuestoNotFoundException(id));
    }

    public List<RepuestoResponse> buscarPorDivision(String division) {
        return repositorio.findByDivisionIgnoreCase(division).stream()
                .map(this::toResponse).toList();
    }

    // TODO 15
    public PageResponse<RepuestoResponse> listarPaginado(int pagina, int tamano, String ordenarPor) {
        return null; // implementar
    }

    public List<RepuestoResponse> buscarConStockBajo(int umbral) {
        return repositorio.findRepuestosConStockBajo(umbral).stream()
                .map(this::toResponse).toList();
    }

    public List<RepuestoResponse> buscar(String termino) {
        return repositorio.buscarEnDescripcion(termino).stream()
                .map(this::toResponse).toList();
    }

    // TODO 13: @Transactional
    public RepuestoResponse crear(RepuestoRequest request) {
        // TODO 14: usar existsByCodigo en lugar de findByCodigo().ifPresent()
        Repuesto nuevo = toModel(request);
        return toResponse(repositorio.save(nuevo));
    }

    // TODO 13: @Transactional
    public RepuestoResponse actualizar(Long id, RepuestoRequest request) {
        Repuesto existente = repositorio.findById(id)
                .orElseThrow(() -> new RepuestoNotFoundException(id));
        existente.setCodigo(request.getCodigo());
        existente.setDescripcion(request.getDescripcion());
        existente.setDivision(request.getDivision());
        existente.setPrecio(request.getPrecio());
        existente.setStock(request.getStock());
        return toResponse(repositorio.save(existente));
    }

    // TODO 13: @Transactional
    public void eliminar(Long id) {
        if (!repositorio.existsById(id)) throw new RepuestoNotFoundException(id);
        repositorio.deleteById(id);
    }

    private RepuestoResponse toResponse(Repuesto r) {
        // TODO 16: agregar createdAt y updatedAt
        return RepuestoResponse.builder()
                .id(r.getId()).codigo(r.getCodigo())
                .descripcion(r.getDescripcion()).division(r.getDivision())
                .precio(r.getPrecio()).stock(r.getStock())
                .estadoStock(calcularEstado(r.getStock()))
                .build();
    }

    private Repuesto toModel(RepuestoRequest req) {
        return Repuesto.builder()
                .codigo(req.getCodigo()).descripcion(req.getDescripcion())
                .division(req.getDivision()).precio(req.getPrecio())
                .stock(req.getStock()).build();
    }

    private String calcularEstado(int stock) {
        if (stock == 0)  return "AGOTADO";
        if (stock < 10)  return "BAJO";
        if (stock < 50)  return "NORMAL";
        return "ALTO";
    }
}
