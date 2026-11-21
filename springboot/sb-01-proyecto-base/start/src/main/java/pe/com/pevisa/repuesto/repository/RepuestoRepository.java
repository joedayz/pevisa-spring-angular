package pe.com.pevisa.repuesto.repository;

import pe.com.pevisa.repuesto.model.Repuesto;

import java.util.List;
import java.util.Optional;

public interface RepuestoRepository {
    Optional<Repuesto> findById(Long id );

    Optional<Repuesto> findByCodigo(String codigo);

    Repuesto save(Repuesto repuesto);

    List<Repuesto> findByDivision(String division);

    List<Repuesto>  findAll();
}
