package pe.com.pevisa.repuesto.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Resultado del procedimiento almacenado resumen_ventas_repuesto().
 * No mapea a ninguna tabla — es el resultado de una función de BD.
 */
@Data
@Builder
public class ResumenVentasResponse {
    private String codigoRepuesto;
    private Long totalUnidades;
    private BigDecimal totalIngresos;
    private Long numPedidos;
}
