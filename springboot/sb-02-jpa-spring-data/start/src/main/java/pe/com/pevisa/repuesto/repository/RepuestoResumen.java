package pe.com.pevisa.repuesto.repository;

import java.math.BigDecimal;

/**
 * Proyección de Spring Data.
 *
 * En vez de traer el objeto Repuesto completo (con todos sus campos),
 * Spring Data puede mapear solo los campos que necesitas.
 *
 * Hibernate genera: SELECT r.id, r.codigo, r.precio FROM repuestos r
 * en lugar de: SELECT * FROM repuestos
 *
 * Esto importa cuando la entidad tiene muchos campos, relaciones lazy
 * o campos de tipo BLOB/CLOB que no quieres traer en una lista.
 *
 * Es una interfaz — Spring Data genera la implementación en tiempo de arranque.
 */
public interface RepuestoResumen {
    Long getId();
    String getCodigo();
    BigDecimal getPrecio();
}
