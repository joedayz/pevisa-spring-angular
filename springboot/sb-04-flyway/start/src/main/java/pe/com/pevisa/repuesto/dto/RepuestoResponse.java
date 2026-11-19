package pe.com.pevisa.repuesto.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * TODO 3: Agregar campo marca (String).
 */
@Data
@Builder
public class RepuestoResponse {
    private Long id;
    private String codigo;
    private String descripcion;
    private String division;
    // TODO 3: agregar marca
    private BigDecimal precio;
    private Integer stock;
    private String estadoStock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
