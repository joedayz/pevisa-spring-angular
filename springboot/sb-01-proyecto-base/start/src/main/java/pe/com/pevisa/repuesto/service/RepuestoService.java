package pe.com.pevisa.repuesto.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.com.pevisa.repuesto.dto.RepuestoResponse;
import pe.com.pevisa.repuesto.model.Repuesto;
import pe.com.pevisa.repuesto.repository.RepuestoRepository;

@Service
@RequiredArgsConstructor
public class RepuestoService {

    private final RepuestoRepository repositorio;

    public RepuestoResponse buscarPorId(Long id){
        return repositorio.findById(id)
                .map(r -> toResponse(r))
                .orElseThrow(() -> new RuntimeException("Repuesto no encontrado con id: " + id));
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
}
