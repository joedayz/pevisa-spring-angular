package pe.com.pevisa.repuesto.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.pevisa.repuesto.dto.PageResponse;
import pe.com.pevisa.repuesto.dto.RepuestoRequest;
import pe.com.pevisa.repuesto.dto.RepuestoResponse;
import pe.com.pevisa.repuesto.exception.RepuestoNotFoundException;
import pe.com.pevisa.repuesto.model.Repuesto;
import pe.com.pevisa.repuesto.repository.RepuestoRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * TODO 15: Implementar listarPaginado(int pagina, int tamano, String ordenarPor):
 *   - Construir PageRequest.of(pagina, tamano, Sort.by(ordenarPor))
 *   - Llamar repositorio.findAll(pageRequest) → Page<Repuesto>
 *   - Convertir a PageResponse<RepuestoResponse> con toda la metadata
 * <p>
 * TODO 16: Actualizar toResponse() para incluir createdAt y updatedAt.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
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

    @Transactional
    public RepuestoResponse crear(RepuestoRequest request) {
        // TODO 14: usar existsByCodigo en lugar de findByCodigo().ifPresent()
        Repuesto nuevo = toModel(request);
        return toResponse(repositorio.save(nuevo));
    }

    @Transactional
    public RepuestoResponse actualizar(Long id, RepuestoRequest request) {
        Repuesto existente = repositorio.findById(id)
                .orElseThrow(() -> new RepuestoNotFoundException(id));
        existente.setCodigo(request.codigo());
        existente.setDescripcion(request.descripcion());
        existente.setDivision(request.division());
        existente.setPrecio(request.precio());
        existente.setStock(request.stock());
        return toResponse(repositorio.save(existente));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repositorio.existsById(id)) throw new RepuestoNotFoundException(id);
        repositorio.deleteById(id);
    }

    private RepuestoResponse toResponse(Repuesto r) {
        return new RepuestoResponse(
                r.getId(), r.getCodigo(), r.getDescripcion(),
                r.getDivision(), r.getPrecio(), r.getStock(),
                LocalDateTime.now(), LocalDateTime.now());

    }

    private Repuesto toModel(RepuestoRequest req) {
        return Repuesto.builder()
                .codigo(req.codigo()).descripcion(req.descripcion())
                .division(req.division()).precio(req.precio())
                .stock(req.stock()).build();
    }

    private String calcularEstado(int stock) {
        if (stock == 0) return "AGOTADO";
        if (stock < 10) return "BAJO";
        if (stock < 50) return "NORMAL";
        return "ALTO";
    }
}
