package pe.com.pevisa.repuesto.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class RepuestoResponse {
    private Long id;
    private String codigo;
    private String descripcion;
    private String division;
    private String marca;           // nuevo en SB-04
    private BigDecimal precio;
    private Integer stock;
    private String estadoStock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
