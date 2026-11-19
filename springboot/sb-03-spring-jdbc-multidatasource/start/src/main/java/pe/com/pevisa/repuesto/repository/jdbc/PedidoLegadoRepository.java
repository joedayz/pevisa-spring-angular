package pe.com.pevisa.repuesto.repository.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import pe.com.pevisa.repuesto.dto.ResumenVentasResponse;
import pe.com.pevisa.repuesto.model.Pedido;

import java.util.List;
import java.util.Optional;

/**
 * TODO 8: Completar el constructor para inyectar el JdbcClient correcto.
 *         Usar @Qualifier("legadoJdbcClient") para evitar ambigüedad.
 *         Pregunta: ¿qué error obtendríamos si no usamos @Qualifier?
 *
 * TODO 9: Implementar findAll():
 *         jdbc.sql("SELECT * FROM pedidos ORDER BY fecha_pedido DESC")
 *             .query(Pedido.class).list()
 *
 * TODO 10: Implementar findByNumeroPedido(String numeroPedido):
 *         Usar parámetro nombrado :numero y .optional() al final.
 *         Pregunta: ¿por qué .optional() y no .single()?
 *
 * TODO 11: Implementar findByCodigoRepuesto(String codigoRepuesto):
 *         WHERE codigo_repuesto = :codigo
 *
 * TODO 12: Implementar findByEstado(String estado)
 *
 * TODO 13: Implementar contarPorEstado(String estado):
 *         Retorna long — usar .query(Long.class).single()
 *
 * TODO 14: Implementar resumenVentas(String codigoRepuesto):
 *         SELECT * FROM resumen_ventas_repuesto(:codigo)
 *         Mapea a ResumenVentasResponse.class y retorna .optional()
 *
 * TODO 15 (desafío): Implementar findAllConMapeoManual() usando RowMapper explícito
 *         con lambda (rs, rowNum) -> Pedido.builder()...build()
 *         ¿Cuándo usarías RowMapper manual en vez del mapeo automático?
 */
@Slf4j
@Repository
public class PedidoLegadoRepository {

    private final JdbcClient jdbc;

    // TODO 8: agregar @Qualifier
    public PedidoLegadoRepository(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    // TODO 9
    public List<Pedido> findAll() {
        return List.of();
    }

    // TODO 10
    public Optional<Pedido> findByNumeroPedido(String numeroPedido) {
        return Optional.empty();
    }

    // TODO 11
    public List<Pedido> findByCodigoRepuesto(String codigoRepuesto) {
        return List.of();
    }

    // TODO 12
    public List<Pedido> findByEstado(String estado) {
        return List.of();
    }

    // TODO 13
    public long contarPorEstado(String estado) {
        return 0L;
    }

    // TODO 14
    public Optional<ResumenVentasResponse> resumenVentas(String codigoRepuesto) {
        return Optional.empty();
    }
}
