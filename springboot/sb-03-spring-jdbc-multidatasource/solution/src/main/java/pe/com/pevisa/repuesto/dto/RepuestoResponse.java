package pe.com.pevisa.repuesto.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de salida — ahora incluye los campos de auditoría.
 * El cliente puede ver cuándo se creó y actualizó el repuesto.
 */
@Data
@Builder
public class RepuestoResponse {

    private Long id;
    private String codigo;
    private String descripcion;
    private String division;
    private BigDecimal precio;
    private Integer stock;
    private String estadoStock;

    // Nuevos en SB-02 — vienen de los campos de auditoría de la entidad
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
