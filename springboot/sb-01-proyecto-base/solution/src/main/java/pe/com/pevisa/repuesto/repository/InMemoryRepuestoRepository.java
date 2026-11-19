package pe.com.pevisa.repuesto.repository;

import org.springframework.stereotype.Repository;
import pe.com.pevisa.repuesto.model.Repuesto;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Implementación en memoria del repositorio.
 *
 * ConcurrentHashMap + AtomicLong garantizan que no haya condiciones de
 * carrera si dos requests llegan al mismo tiempo.
 * En SB-05 (Concurrencia) explicamos esto con más detalle.
 *
 * Esta implementación desaparece en SB-02, donde Spring Data JPA
 * genera la implementación real contra Postgres automáticamente.
 * Pero la interfaz RepuestoRepository no cambia — el Service tampoco.
 */
@Repository
public class InMemoryRepuestoRepository implements RepuestoRepository {

    private final Map<Long, Repuesto> almacen = new ConcurrentHashMap<>();
    private final AtomicLong secuencia = new AtomicLong(1);

    public InMemoryRepuestoRepository() {
        // Datos de ejemplo con repuestos reales de PEVISA
        save(Repuesto.builder()
                .codigo("EMP-TOYOTA-001")
                .descripcion("Empaquetadura de motor Toyota Corolla 1.8")
                .division("EMPAQUETADURAS")
                .precio(new BigDecimal("185.00"))
                .stock(42)
                .build());

        save(Repuesto.builder()
                .codigo("FIL-BOSCH-002")
                .descripcion("Filtro de aceite Bosch universal")
                .division("BOSCH")
                .precio(new BigDecimal("28.50"))
                .stock(150)
                .build());

        save(Repuesto.builder()
                .codigo("BAT-VOLTA-003")
                .descripcion("Batería Volta 60Ah 12V")
                .division("BATERIAS")
                .precio(new BigDecimal("320.00"))
                .stock(8)
                .build());

        save(Repuesto.builder()
                .codigo("BUJ-DENSO-004")
                .descripcion("Bujía Denso Iridium IT20")
                .division("DENSO")
                .precio(new BigDecimal("45.00"))
                .stock(0)
                .build());
    }

    @Override
    public List<Repuesto> findAll() {
        return new ArrayList<>(almacen.values());
    }

    @Override
    public Optional<Repuesto> findById(Long id) {
        return Optional.ofNullable(almacen.get(id));
    }

    @Override
    public Optional<Repuesto> findByCodigo(String codigo) {
        return almacen.values().stream()
                .filter(r -> r.getCodigo().equalsIgnoreCase(codigo))
                .findFirst();
    }

    @Override
    public List<Repuesto> findByDivision(String division) {
        return almacen.values().stream()
                .filter(r -> r.getDivision().equalsIgnoreCase(division))
                .collect(Collectors.toList());
    }

    @Override
    public Repuesto save(Repuesto repuesto) {
        if (repuesto.getId() == null) {
            repuesto.setId(secuencia.getAndIncrement());
        }
        almacen.put(repuesto.getId(), repuesto);
        return repuesto;
    }

    @Override
    public void deleteById(Long id) {
        almacen.remove(id);
    }

    @Override
    public boolean existsById(Long id) {
        return almacen.containsKey(id);
    }
}
