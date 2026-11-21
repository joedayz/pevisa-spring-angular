package pe.com.pevisa.repuesto.repository;

import org.springframework.stereotype.Repository;
import pe.com.pevisa.repuesto.model.Repuesto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryRepuestoRepository implements RepuestoRepository {

    private final Map<Long, Repuesto> almacen = new ConcurrentHashMap<>();
    private final AtomicLong secuencia = new AtomicLong(1);

    public InMemoryRepuestoRepository(){
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
    public Optional<Repuesto> findById(Long id) {
        return Optional.ofNullable(almacen.get(id));
    }

    @Override
    public Optional<Repuesto> findByCodigo(String codigo) {
        return almacen.values().stream()
                .filter(r -> r.getCodigo().equals(codigo))
                .findFirst();
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
    public List<Repuesto> findByDivision(String division) {
        return almacen.values().stream()
                .filter(r -> r.getDivision().equals(division))
                .toList();
    }

    @Override
    public List<Repuesto> findAll() {
        return List.copyOf(almacen.values());
    }
}
