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
import pe.com.pevisa.repuesto.repository.jpa.RepuestoJpaRepository;

import java.util.List;

/**
 * Sin cambios de lógica respecto a SB-02.
 * Solo actualiza la referencia al nuevo paquete jpa.RepuestoJpaRepository.
 *
 * @Transactional aquí opera sobre el TransactionManager "principalTransactionManager"
 * porque es @Primary. Las operaciones en el datasource legado no pasan por aquí.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RepuestoService {

    private final RepuestoJpaRepository repositorio;

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

    public PageResponse<RepuestoResponse> listarPaginado(int pagina, int tamano, String ordenarPor) {
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
                .map(this::toResponse).toList();
    }

    public List<RepuestoResponse> buscar(String termino) {
        return repositorio.buscarEnDescripcion(termino).stream()
                .map(this::toResponse).toList();
    }

    @Transactional
    public RepuestoResponse crear(RepuestoRequest request) {
        if (repositorio.existsByCodigo(request.getCodigo())) {
            throw new IllegalArgumentException(
                    "Ya existe un repuesto con el código: " + request.getCodigo());
        }
        return toResponse(repositorio.save(toModel(request)));
    }

    @Transactional
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

    @Transactional
    public void eliminar(Long id) {
        if (!repositorio.existsById(id)) throw new RepuestoNotFoundException(id);
        repositorio.deleteById(id);
    }

    private RepuestoResponse toResponse(Repuesto r) {
        return RepuestoResponse.builder()
                .id(r.getId()).codigo(r.getCodigo())
                .descripcion(r.getDescripcion()).division(r.getDivision())
                .precio(r.getPrecio()).stock(r.getStock())
                .estadoStock(calcularEstado(r.getStock()))
                .createdAt(r.getCreatedAt()).updatedAt(r.getUpdatedAt())
                .build();
    }

    private Repuesto toModel(RepuestoRequest req) {
        return Repuesto.builder()
                .codigo(req.getCodigo()).descripcion(req.getDescripcion())
                .division(req.getDivision()).precio(req.getPrecio())
                .stock(req.getStock()).build();
    }

    private String calcularEstado(int stock) {
        if (stock == 0) return "AGOTADO";
        if (stock < 10) return "BAJO";
        if (stock < 50) return "NORMAL";
        return "ALTO";
    }
}
