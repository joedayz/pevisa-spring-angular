package pe.com.pevisa.repuesto.dto;

import java.math.BigDecimal;

/**
 * TODO 4: Agregar @Data de Lombok
 *
 * TODO 5: Agregar anotaciones de validación en cada campo:
 *   - codigo:      @NotBlank, @Size(max=50)
 *   - descripcion: @NotBlank, @Size(max=200)
 *   - division:    @NotBlank
 *   - precio:      @NotNull, @DecimalMin("0.01")
 *   - stock:       @NotNull, @Min(0)
 */
public class RepuestoRequest {

    private String codigo;
    private String descripcion;
    private String division;
    private BigDecimal precio;
    private Integer stock;
}
