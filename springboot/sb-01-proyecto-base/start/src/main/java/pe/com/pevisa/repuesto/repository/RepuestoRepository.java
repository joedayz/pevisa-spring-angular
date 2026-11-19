package pe.com.pevisa.repuesto.repository;

import pe.com.pevisa.repuesto.model.Repuesto;

import java.util.Optional;

public interface RepuestoRepository {
    Optional<Repuesto> findById(Long id );
}
