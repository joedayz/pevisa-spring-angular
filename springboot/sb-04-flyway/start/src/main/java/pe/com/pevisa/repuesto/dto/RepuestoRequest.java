package pe.com.pevisa.repuesto.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

/**
 * TODO 2: Agregar campo marca con @NotBlank y @Size(max=100).
 */
@Data
public class RepuestoRequest {

    @NotBlank(message = "El código es obligatorio")
    @Size(max = 50)
    private String codigo;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 200)
    private String descripcion;

    @NotBlank(message = "La división es obligatoria")
    private String division;

    // TODO 2: agregar marca

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01")
    private BigDecimal precio;

    @NotNull(message = "El stock es obligatorio")
    @Min(0)
    private Integer stock;
}
