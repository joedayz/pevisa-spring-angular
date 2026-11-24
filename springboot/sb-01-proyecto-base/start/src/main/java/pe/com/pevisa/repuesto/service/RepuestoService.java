package pe.com.pevisa.repuesto.service;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.com.pevisa.repuesto.dto.RepuestoRequest;
import pe.com.pevisa.repuesto.dto.RepuestoResponse;
import pe.com.pevisa.repuesto.exception.RepuestoNotFoundException;
import pe.com.pevisa.repuesto.model.Repuesto;
import pe.com.pevisa.repuesto.repository.RepuestoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RepuestoService {

    private final RepuestoRepository repositorio;

    public RepuestoResponse buscarPorId(Long id){
        return repositorio.findById(id)
                .map(r -> toResponse(r))
                .orElseThrow(() -> new RepuestoNotFoundException(id));
    }

    private RepuestoResponse toResponse(Repuesto r) {
        return new RepuestoResponse(
                r.getId(),
                r.getCodigo(),
                r.getDescripcion(),
                r.getDivision(),
                r.getPrecio(),
                r.getStock()
        );
    }

    public RepuestoResponse crear(RepuestoRequest request) {

        repositorio.findByCodigo(request.codigo())
                .ifPresent(r -> {
                    throw new IllegalArgumentException("Repuesto ya existe con codigo: " + request.codigo());
                });
        Repuesto nuevo = toModel(request);
        Repuesto guardado = repositorio.save(nuevo);
        return toResponse(guardado);
    }

    private Repuesto toModel(RepuestoRequest request) {
        return Repuesto.builder()
                .codigo(request.codigo())
                .descripcion(request.descripcion())
                .division(request.division())
                .precio(request.precio())
                .stock(request.stock())
                .build();
    }

    public List<RepuestoResponse> buscarPorDivision(String division) {

        return repositorio.findByDivision(division)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<RepuestoResponse> listar() {
        return repositorio.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public RepuestoResponse actualizar(Long id, @Valid RepuestoRequest request) {

        Repuesto existente = repositorio.findById(id)
                .orElseThrow(() -> new RepuestoNotFoundException(id));

        existente.setCodigo(request.codigo());
        existente.setDescripcion(request.descripcion());
        existente.setDivision(request.division());
        existente.setPrecio(request.precio());
        existente.setStock(request.stock());

        return toResponse(repositorio.save(existente));
    }

    public void eliminar(Long id) {
        if(!repositorio.existsById(id)){
            throw new RepuestoNotFoundException(id);
        }
        repositorio.deleteById(id);
    }
}
