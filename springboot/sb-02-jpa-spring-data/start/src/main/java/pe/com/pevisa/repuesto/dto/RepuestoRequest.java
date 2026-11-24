package pe.com.pevisa.repuesto.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record RepuestoRequest(
                              @NotBlank(message = "El código es obligatorio")
                              @Size(max = 50, message = "El código no puede tener más de 50 caracteres")
                              String codigo,
                              @NotBlank(message = "La descripción es obligatoria")
                              @Size(max = 200, message = "La descripción no puede superar 200 caracteres")
                              String descripcion,
                              @NotBlank(message = "La división es obligatoria")
                              String division,
                              @NotNull(message = "El precio es obligatorio")
                              @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
                              BigDecimal precio,
                              @NotNull(message = "El stock es obligatorio")
                              @Min(value = 0, message = "El stock no puede ser negativo")
                              Integer stock) {
}
