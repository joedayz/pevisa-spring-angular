package pe.com.pevisa.repuesto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;


public record RepuestoResponse(
    Long id,
    String codigo,
    String descripcion,
    String division,
    BigDecimal precio,
    Integer stock
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