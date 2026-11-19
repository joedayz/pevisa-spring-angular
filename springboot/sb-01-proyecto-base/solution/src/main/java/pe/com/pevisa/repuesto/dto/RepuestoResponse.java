package pe.com.pevisa.repuesto.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO de salida — lo que el cliente recibe en el JSON de respuesta.
 *
 * Separar Request y Response permite evolucionar cada contrato
 * de forma independiente sin romper la API ni el modelo interno.
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

    /** Estado calculado en base al stock — no viene de la BD */
    private String estadoStock;
}
