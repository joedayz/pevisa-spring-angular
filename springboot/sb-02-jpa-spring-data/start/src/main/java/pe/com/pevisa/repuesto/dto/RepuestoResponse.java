package pe.com.pevisa.repuesto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public record RepuestoResponse(
    Long id,
    String codigo,
    String descripcion,
    String division,
    BigDecimal precio,
    Integer stock,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

    @JsonProperty("estadoStock")
    public String estadoStock() {
        if (stock == null || stock <= 0) {
            return "AGOTADO";
        } else if (stock < 5) {
            return "POCO STOCK";
        } else {
            return "DISPONIBLE";
        }
    }
}