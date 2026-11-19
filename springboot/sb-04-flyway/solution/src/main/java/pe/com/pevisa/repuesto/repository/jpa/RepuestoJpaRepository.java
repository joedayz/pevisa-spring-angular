package pe.com.pevisa.repuesto.repository.jpa;

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
 * Repositorio JPA del datasource principal.
 *
 * CAMBIO ESTRUCTURAL respecto a SB-02:
 * Movido a subpaquete "jpa" para que @EnableJpaRepositories(basePackages = "...jpa")
 * en DataSourceConfig lo encuentre y lo asocie al datasource principal.
 *
 * Si dejáramos todos los repositorios en el mismo paquete, Spring Data
 * intentaría gestionar PedidoLegadoRepository con JPA también — y fallaría
 * porque no tiene @Entity asociada.
 *
 * El contenido es el mismo de SB-02.
 */
public interface RepuestoJpaRepository extends JpaRepository<Repuesto, Long> {

    Optional<Repuesto> findByCodigo(String codigo);
    List<Repuesto> findByDivisionIgnoreCase(String division);
    boolean existsByCodigo(String codigo);
    Page<Repuesto> findByDivision(String division, Pageable pageable);

    @Query("SELECT r FROM Repuesto r WHERE r.stock < :umbral ORDER BY r.stock ASC")
    List<Repuesto> findRepuestosConStockBajo(@Param("umbral") int umbral);

    @Query("SELECT r FROM Repuesto r WHERE LOWER(r.descripcion) LIKE LOWER(CONCAT('%', :termino, '%'))")
    List<Repuesto> buscarEnDescripcion(@Param("termino") String termino);

    @Query("SELECT SUM(r.precio * r.stock) FROM Repuesto r WHERE r.division = :division")
    BigDecimal calcularValorInventarioPorDivision(@Param("division") String division);
}
