package pe.com.pevisa.repuesto.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.com.pevisa.repuesto.model.Repuesto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * TODO 6: Hacer que esta interfaz extienda JpaRepository<Repuesto, Long>
 *         e importar org.springframework.data.jpa.repository.JpaRepository
 *
 *         Una vez que lo hagas, los métodos findAll(), findById(), save(),
 *         deleteById(), existsById() desaparecen de aquí — JpaRepository los hereda.
 *         InMemoryRepuestoRepository desaparece completamente.
 *
 * TODO 7: Agregar consultas derivadas (Spring Data las genera solas):
 *   - findByCodigo(String codigo)
 *   - findByDivisionIgnoreCase(String division)
 *   - existsByCodigo(String codigo)
 *   - findByDivisionAndStockLessThan(String division, int stock)
 *
 * TODO 8: Paginación — agregar:
 *   - findByDivision(String division, Pageable pageable)
 *   Pregunta: ¿qué clase concreta de Pageable usarías en el Service?
 *
 * TODO 9: Consulta JPQL con @Query:
 *   - findRepuestosConStockBajo(@Param("umbral") int umbral)
 *     SQL: SELECT r FROM Repuesto r WHERE r.stock < :umbral ORDER BY r.stock ASC
 *
 * TODO 10: Consulta JPQL de búsqueda en texto:
 *   - buscarEnDescripcion(@Param("termino") String termino)
 *     SQL: LOWER(r.descripcion) LIKE LOWER(CONCAT('%', :termino, '%'))
 *
 * TODO 11: Consulta de agregación JPQL:
 *   - calcularValorInventarioPorDivision(@Param("division") String division)
 *     Retorna BigDecimal con SUM(r.precio * r.stock)
 */
public interface RepuestoRepository /* TODO 6 */ {

    // TODO 7, 8, 9, 10, 11 — definir los métodos aquí
}
