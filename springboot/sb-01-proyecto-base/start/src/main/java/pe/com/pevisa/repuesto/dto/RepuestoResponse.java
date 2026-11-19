package pe.com.pevisa.repuesto.dto;

import java.math.BigDecimal;

/**
 * TODO 6: Agregar @Data y @Builder de Lombok
 *
 * TODO 7: Agregar el campo estadoStock (String) — se calculará en el Service,
 *         no viene de la BD. ¿Por qué no lo ponemos en el modelo?
 */
public class RepuestoResponse {

    private Long id;
    private String codigo;
    private String descripcion;
    private String division;
    private BigDecimal precio;
    private Integer stock;
}
