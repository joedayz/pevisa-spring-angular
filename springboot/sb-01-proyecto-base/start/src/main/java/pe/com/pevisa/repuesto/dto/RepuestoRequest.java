package pe.com.pevisa.repuesto.dto;

import java.math.BigDecimal;

public record RepuestoRequest(String codigo,
                              String descripcion,
                              String division,
                              BigDecimal precio,
                              Integer stock) {
}
