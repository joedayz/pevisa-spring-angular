package pe.com.pevisa.repuesto.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Modelo del sistema legado — NO es una entidad JPA.
 *
 * Esta clase representa una fila de la tabla "pedidos" en el datasource legado.
 * No tiene @Entity ni @Table porque no la gestiona Hibernate.
 * JdbcClient la mapea desde un ResultSet usando RowMapper o el mapeo automático.
 *
 * Regla: cada datasource tiene sus propios modelos.
 * Mezclar entidades JPA con objetos del datasource legado en el mismo modelo
 * genera acoplamiento difícil de mantener.
 */
@Data
@Builder
public class Pedido {

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
