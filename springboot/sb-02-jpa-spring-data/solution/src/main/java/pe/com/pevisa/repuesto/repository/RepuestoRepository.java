package pe.com.pevisa.repuesto.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.com.pevisa.repuesto.model.Repuesto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA — reemplaza InMemoryRepuestoRepository de SB-01.
 *
 * CAMBIOS respecto a SB-01:
 *   - En vez de implementar nuestra interfaz propia, extendemos JpaRepository<T, ID>.
 *   - Spring Data genera la implementación en tiempo de arranque. No hay código de acceso a datos.
 *   - InMemoryRepuestoRepository desaparece completamente.
 *   - RepuestoService NO cambió ni una línea.
 *
 * JpaRepository hereda:
 *   findAll(), findById(), save(), deleteById(), existsById(), count()…
 *   todo lo que teníamos en nuestra interfaz manual + muchos más.
 *
 * CONSULTAS DERIVADAS (query by method name):
 *   Spring Data lee el nombre del método y genera el SQL automáticamente.
 *   findByDivision("BOSCH") → SELECT * FROM repuestos WHERE division = 'BOSCH'
 *
 * CONSULTAS JPQL (@Query):
 *   Para lógica que el nombre del método no puede expresar limpiamente.
 *   JPQL opera sobre entidades (Repuesto), no sobre tablas (repuestos).
 *
 * CONSULTAS NATIVAS (@Query nativeQuery = true):
 *   SQL puro cuando necesitas funciones específicas de Postgres (JSONB, full-text, etc.).
 */
public interface RepuestoRepository extends JpaRepository<Repuesto, Long> {

    // ── Consultas derivadas ────────────────────────────────────────────────

    Optional<Repuesto> findByCodigo(String codigo);

    List<Repuesto> findByDivision(String division);

    // findBy + And: WHERE division = ? AND stock < ?
    List<Repuesto> findByDivisionAndStockLessThan(String division, int stock);

    // Ignora mayúsculas/minúsculas en la BD
    List<Repuesto> findByDivisionIgnoreCase(String division);

    // Verificar existencia sin traer el objeto completo (más eficiente)
    boolean existsByCodigo(String codigo);

    // ── Paginación ─────────────────────────────────────────────────────────

    /**
     * Paginación nativa de Spring Data.
     * El caller pasa Pageable.of(page, size, Sort.by("codigo"))
     * y obtiene un Page<Repuesto> con metadata: total de páginas, total de elementos…
     */
    Page<Repuesto> findByDivision(String division, Pageable pageable);

    // ── JPQL ───────────────────────────────────────────────────────────────

    /**
     * Repuestos con stock bajo el umbral, ordenados por stock ascendente.
     * El parámetro es nombrado (@Param) para mayor claridad.
     */
    @Query("SELECT r FROM Repuesto r WHERE r.stock < :umbral ORDER BY r.stock ASC")
    List<Repuesto> findRepuestosConStockBajo(@Param("umbral") int umbral);

    /**
     * Búsqueda de texto libre en descripción (LIKE con comodines).
     * %:termino% → contiene el término en cualquier posición.
     */
    @Query("SELECT r FROM Repuesto r WHERE LOWER(r.descripcion) LIKE LOWER(CONCAT('%', :termino, '%'))")
    List<Repuesto> buscarEnDescripcion(@Param("termino") String termino);

    /**
     * Proyección: solo trae id, codigo y precio — no el objeto completo.
     * Útil cuando necesitas una lista ligera para un dropdown o autocomplete.
     */
    @Query("SELECT r.id as id, r.codigo as codigo, r.precio as precio FROM Repuesto r WHERE r.division = :division")
    List<RepuestoResumen> findResumenPorDivision(@Param("division") String division);

    /**
     * Suma el valor total del inventario de una división.
     * Función de agregación en JPQL.
     */
    @Query("SELECT SUM(r.precio * r.stock) FROM Repuesto r WHERE r.division = :division")
    BigDecimal calcularValorInventarioPorDivision(@Param("division") String division);

    // ── Consulta nativa ────────────────────────────────────────────────────

    /**
     * Búsqueda full-text nativa de PostgreSQL.
     * to_tsvector / to_tsquery son funciones propias de Postgres
     * que no existen en JPQL estándar.
     *
     * Solo disponible con Postgres real — en tests con H2 se salta este método.
     */
    @Query(value = """
            SELECT * FROM repuestos
            WHERE to_tsvector('spanish', descripcion) @@ plainto_tsquery('spanish', :termino)
            """, nativeQuery = true)
    List<Repuesto> busquedaFullText(@Param("termino") String termino);
}
