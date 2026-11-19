package pe.com.pevisa.repuesto.repository;

import org.springframework.stereotype.Repository;
import pe.com.pevisa.repuesto.model.Repuesto;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * TODO 13: Completar los métodos del repositorio en memoria.
 *
 * ConcurrentHashMap y AtomicLong ya están provistos — úsalos.
 * No uses HashMap ni int++ para los ids (¿por qué no son seguros con concurrencia?).
 */
@Repository
public class InMemoryRepuestoRepository implements RepuestoRepository {

    private final Map<Long, Repuesto> almacen = new ConcurrentHashMap<>();
    private final AtomicLong secuencia = new AtomicLong(1);

    public InMemoryRepuestoRepository() {
        // TODO 14: Pre-cargar al menos 3 repuestos de PEVISA con datos reales
        // Divisiones posibles: EMPAQUETADURAS, BOSCH, BATERIAS, DENSO
    }

    @Override
    public List<Repuesto> findAll() {
        // TODO 15
        return Collections.emptyList();
    }

    @Override
    public Optional<Repuesto> findById(Long id) {
        // TODO 16
        return Optional.empty();
    }

    @Override
    public Optional<Repuesto> findByCodigo(String codigo) {
        // TODO 17: usar stream + filter, comparar ignorando mayúsculas
        return Optional.empty();
    }

    @Override
    public List<Repuesto> findByDivision(String division) {
        // TODO 18: usar stream + filter
        return Collections.emptyList();
    }

    @Override
    public Repuesto save(Repuesto repuesto) {
        // TODO 19: si id es null, asignar el siguiente de la secuencia
        //          luego guardar en el mapa y retornar
        return repuesto;
    }

    @Override
    public void deleteById(Long id) {
        // TODO 20
    }

    @Override
    public boolean existsById(Long id) {
        // TODO 21
        return false;
    }
}
