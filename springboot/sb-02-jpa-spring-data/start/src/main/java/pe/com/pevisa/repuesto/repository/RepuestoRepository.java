package pe.com.pevisa.repuesto.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.com.pevisa.repuesto.model.Repuesto;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**

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
public interface RepuestoRepository extends JpaRepository<Repuesto, Long> {

    // Consultas derivadas
    Optional<Repuesto> findByCodigo(String codigo);

    List<Repuesto> findByDivision(String division);

    // findBy + And: WHERE division = ? and stock < ?
    List<Repuesto> findByDivisionAndStockLessThan(String division, int stock);

    List<Repuesto> findByDivisionIgnoreCase(String division);

    boolean existsByCodigo(String codigo);

    @Query("SELECT r FROM Repuesto r WHERE r.stock < :umbral ORDER BY r.stock ASC")
    List<Repuesto> findRepuestosConStockBajo(@Param("umbral") int umbral);

    @Query("SELECT r FROM Repuesto r WHERE LOWER(r.descripcion) LIKE LOWER(CONCAT('%', :termino, '%'))")
    List<Repuesto> buscarEnDescripcion(@Param("termino") String termino);

    // Paginación

    // JPQL
}
