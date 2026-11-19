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
 * Repositorio del datasource legado — usa JdbcClient (Spring 6).
 *
 * ¿POR QUÉ JdbcClient Y NO JPA AQUÍ?
 *
 *   1. El schema del legado no lo controlamos — puede tener nombres de columna
 *      raros, tipos propios, o convenciones que no encajan con @Entity.
 *
 *   2. Es mayormente lectura — no necesitamos el ciclo de vida de Hibernate
 *      (dirty checking, caché de primer nivel, flush, etc.).
 *
 *   3. Las queries son SQL específico de esa BD — procedimientos almacenados,
 *      funciones propias, uniones complejas que JPQL no puede expresar bien.
 *
 *   4. Rendimiento predecible — JdbcClient ejecuta exactamente el SQL que ves.
 *      Sin sorpresas de N+1, sin lazy loading que explota en producción.
 *
 * EVOLUCIÓN DE LA API JDBC EN SPRING:
 *   Spring 1.x:  JdbcTemplate → funcional pero verbose, ResultSet manual
 *   Spring 5.x:  NamedParameterJdbcTemplate → parámetros nombrados
 *   Spring 6.1:  JdbcClient → fluido, encadenable, mapeo automático a records/POJOs
 *
 * @Qualifier("legadoJdbcClient") → inyecta el JdbcClient del datasource legado,
 * no el del datasource principal.
 */
@Slf4j
@Repository
public class PedidoLegadoRepository {

    private final JdbcClient jdbc;

    public PedidoLegadoRepository(
            @Qualifier("legadoJdbcClient") JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    // ── Consultas básicas ─────────────────────────────────────────────────

    /**
     * JdbcClient: API fluida en 3 pasos:
     *   .sql("...")          → define la query
     *   .param("x", value)  → named parameter (evita SQL injection)
     *   .query(Clase.class)  → mapea automáticamente columna → campo
     *                         (snake_case a camelCase por convención)
     */
    public List<Pedido> findAll() {
        log.info("Consultando todos los pedidos en datasource legado");
        return jdbc.sql("SELECT * FROM pedidos ORDER BY fecha_pedido DESC")
                .query(Pedido.class)
                .list();
    }

    public Optional<Pedido> findByNumeroPedido(String numeroPedido) {
        return jdbc.sql("SELECT * FROM pedidos WHERE numero_pedido = :numero")
                .param("numero", numeroPedido)
                .query(Pedido.class)
                .optional();
    }

    /**
     * Consulta con múltiples parámetros nombrados.
     * Más legible que posicional (?) cuando hay varios parámetros.
     */
    public List<Pedido> findByCodigoRepuesto(String codigoRepuesto) {
        log.info("Buscando pedidos del repuesto={}", codigoRepuesto);
        return jdbc.sql("""
                SELECT * FROM pedidos
                WHERE codigo_repuesto = :codigo
                ORDER BY fecha_pedido DESC
                """)
                .param("codigo", codigoRepuesto)
                .query(Pedido.class)
                .list();
    }

    public List<Pedido> findByEstado(String estado) {
        return jdbc.sql("SELECT * FROM pedidos WHERE estado = :estado ORDER BY fecha_pedido DESC")
                .param("estado", estado)
                .query(Pedido.class)
                .list();
    }

    /**
     * Consulta con múltiples parámetros de distintos tipos.
     * JdbcClient acepta cualquier tipo que JDBC soporte.
     */
    public List<Pedido> findByDistribuidorYEstado(String distribuidor, String estado) {
        return jdbc.sql("""
                SELECT * FROM pedidos
                WHERE distribuidor = :distribuidor
                  AND estado = :estado
                ORDER BY fecha_pedido DESC
                """)
                .param("distribuidor", distribuidor)
                .param("estado", estado)
                .query(Pedido.class)
                .list();
    }

    // ── Agregaciones ─────────────────────────────────────────────────────

    /**
     * Mapeo a un tipo simple (.query(Long.class)) cuando la query
     * devuelve una sola columna de un tipo primitivo o wrapper.
     */
    public long contarPorEstado(String estado) {
        return jdbc.sql("SELECT COUNT(*) FROM pedidos WHERE estado = :estado")
                .param("estado", estado)
                .query(Long.class)
                .single();
    }

    // ── Procedimiento almacenado ──────────────────────────────────────────

    /**
     * Llamada a un procedimiento almacenado de Postgres (función PL/pgSQL).
     *
     * JdbcClient puede llamar funciones que devuelven TABLE con SELECT * FROM func().
     * El mapeo columna → campo es automático mientras los nombres coincidan
     * (snake_case en SQL → camelCase en Java).
     *
     * En Oracle usaría: {call resumen_ventas_repuesto(?, ?)}
     * con SimpleJdbcCall para procedimientos que usan OUT parameters.
     */
    public Optional<ResumenVentasResponse> resumenVentas(String codigoRepuesto) {
        log.info("Ejecutando procedimiento resumen_ventas_repuesto({})", codigoRepuesto);
        return jdbc.sql("SELECT * FROM resumen_ventas_repuesto(:codigo)")
                .param("codigo", codigoRepuesto)
                .query(ResumenVentasResponse.class)
                .optional();
    }

    // ── RowMapper manual ──────────────────────────────────────────────────

    /**
     * Cuando el mapeo automático no funciona (nombres de columna irregulares,
     * tipos personalizados, lógica de transformación) se usa RowMapper explícito.
     *
     * Este ejemplo muestra cómo sería si las columnas tuviesen nombres legacy
     * que no siguen convenciones (ej: sistema AS400, Oracle antiguo).
     */
    public List<Pedido> findAllConMapeoManual() {
        return jdbc.sql("SELECT * FROM pedidos ORDER BY fecha_pedido DESC")
                .query((rs, rowNum) -> Pedido.builder()
                        .id(rs.getLong("id"))
                        .numeroPedido(rs.getString("numero_pedido"))
                        .codigoRepuesto(rs.getString("codigo_repuesto"))
                        .distribuidor(rs.getString("distribuidor"))
                        .cantidad(rs.getInt("cantidad"))
                        .precioUnitario(rs.getBigDecimal("precio_unitario"))
                        .total(rs.getBigDecimal("total"))
                        .estado(rs.getString("estado"))
                        .fechaPedido(rs.getTimestamp("fecha_pedido").toLocalDateTime())
                        .build())
                .list();
    }
}
