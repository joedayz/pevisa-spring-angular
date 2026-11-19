package pe.com.pevisa.repuesto.repository;

import pe.com.pevisa.repuesto.model.Repuesto;

import java.util.List;
import java.util.Optional;

/**
 * TODO 12: Esta interfaz ya está completa — léela y entiende por qué
 *          definimos una interfaz aquí en vez de una clase concreta.
 *
 *          Pregunta: ¿qué cambia en SB-02 cuando usemos JPA?
 *          ¿Tendremos que modificar el Service? ¿Por qué?
 */
public interface RepuestoRepository {

    List<Repuesto> findAll();
    Optional<Repuesto> findById(Long id);
    Optional<Repuesto> findByCodigo(String codigo);
    List<Repuesto> findByDivision(String division);
    Repuesto save(Repuesto repuesto);
    void deleteById(Long id);
    boolean existsById(Long id);
}
