package pe.com.pevisaautoparts.backend.repuesto;

import java.math.BigDecimal;

public record Repuesto(Long id,
                       String codigo,
                       String nombre,
                       String marca,
                       String categoria,
                       Integer stock,
                       BigDecimal precio) {
}
