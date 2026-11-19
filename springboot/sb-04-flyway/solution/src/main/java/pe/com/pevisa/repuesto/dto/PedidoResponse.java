package pe.com.pevisa.repuesto.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PedidoResponse {
    private Long id;
    private String numeroPedido;
    private String codigoRepuesto;
    private String distribuidor;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal total;
    private String estado;
    private LocalDateTime fechaPedido;
}
