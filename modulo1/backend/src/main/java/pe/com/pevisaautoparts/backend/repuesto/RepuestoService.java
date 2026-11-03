package pe.com.pevisaautoparts.backend.repuesto;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class RepuestoService {

    private final List<Repuesto> repuestos = List.of(
            new Repuesto(1L, "FLT-001", "Filtro de aceite", "Mann", "Filtros", 120, new BigDecimal("45.90")),
            new Repuesto(2L, "FLT-014", "Filtro de aire", "Mann", "Filtros", 80, new BigDecimal("62.50")),
            new Repuesto(3L, "FRN-208", "Pastillas de freno delanteras", "Bosch", "Frenos", 40, new BigDecimal("189.00")),
            new Repuesto(4L, "FRN-311", "Disco de freno ventilado", "Brembo", "Frenos", 16, new BigDecimal("320.00")),
            new Repuesto(5L, "SUS-102", "Amortiguador delantero", "Monroe", "Suspensión", 22, new BigDecimal("275.00")),
            new Repuesto(6L, "MOT-050", "Bujía de encendido", "NGK", "Motor", 200, new BigDecimal("28.00")),
            new Repuesto(7L, "ELE-017", "Batería 12V 75Ah", "Bosch", "Eléctrico", 8, new BigDecimal("450.00")),
            new Repuesto(8L, "ACE-003", "Aceite de motor 5W-30 4L", "Castrol", "Lubricantes", 55, new BigDecimal("149.90")));

    public List<Repuesto> listar(){
        return repuestos;
    }

    public Optional<Repuesto> buscarPorId(Long id) {
        return repuestos.stream()
                .filter(repuesto -> repuesto.id().equals(id))
                .findFirst();
    }
}
