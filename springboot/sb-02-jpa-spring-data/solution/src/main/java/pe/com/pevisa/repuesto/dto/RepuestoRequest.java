package pe.com.pevisa.repuesto.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

// Sin cambios respecto a SB-01 — los DTOs son estables.
@Data
public class RepuestoRequest {

    @NotBlank(message = "El código es obligatorio")
    @Size(max = 50, message = "El código no puede superar 50 caracteres")
    private String codigo;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 200, message = "La descripción no puede superar 200 caracteres")
    private String descripcion;

    @NotBlank(message = "La división es obligatoria")
    private String division;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a cero")
    private BigDecimal precio;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;
}
