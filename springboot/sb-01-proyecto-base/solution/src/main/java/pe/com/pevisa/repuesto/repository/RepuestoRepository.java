package pe.com.pevisa.repuesto.repository;

import pe.com.pevisa.repuesto.model.Repuesto;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida hacia el almacén de datos.
 *
 * Definimos una interfaz para que el Service no sepa si los datos
 * vienen de memoria, de una BD o de un servicio externo.
 * Esto nos permite:
 *  1. Testear el Service con un repositorio falso (mock).
 *  2. Cambiar la implementación en SB-02 (JPA) sin tocar el Service.
 *
 * Este patrón es la base de la arquitectura hexagonal que veremos en
 * más profundidad cuando el curso crezca.
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
