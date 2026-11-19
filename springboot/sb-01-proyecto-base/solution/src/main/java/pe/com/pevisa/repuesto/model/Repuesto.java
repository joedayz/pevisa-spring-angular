package pe.com.pevisa.repuesto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Modelo de dominio — representa un repuesto en el catálogo PEVISA.
 *
 * Por ahora vive en memoria. A partir de SB-02 esta clase se convierte
 * en entidad JPA con @Entity y se persiste en base de datos.
 *
 * Usamos BigDecimal para precios porque double/float tienen error de
 * representación binaria: 0.1 + 0.2 ≠ 0.3 con tipos primitivos.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Repuesto {

    private Long id;

    /** Código interno PEVISA, ej: "EMP-TOYOTA-001" */
    private String codigo;

    private String descripcion;

    /** División comercial: BOSCH, DENSO, BATERIAS, EMPAQUETADURAS … */
    private String division;

    private BigDecimal precio;

    private Integer stock;
}
