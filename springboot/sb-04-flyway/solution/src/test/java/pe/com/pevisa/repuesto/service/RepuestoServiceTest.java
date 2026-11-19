package pe.com.pevisa.repuesto.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.com.pevisa.repuesto.dto.RepuestoRequest;
import pe.com.pevisa.repuesto.dto.RepuestoResponse;
import pe.com.pevisa.repuesto.exception.RepuestoNotFoundException;
import pe.com.pevisa.repuesto.model.Repuesto;
import pe.com.pevisa.repuesto.repository.jpa.RepuestoJpaRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RepuestoServiceTest {

    @Mock  RepuestoJpaRepository repositorio;
    @InjectMocks RepuestoService servicio;

    private Repuesto repuesto;

    @BeforeEach
    void setUp() {
        repuesto = Repuesto.builder()
                .id(1L).codigo("EMP-TOYOTA-001")
                .descripcion("Empaquetadura Toyota").division("EMPAQUETADURAS")
                .marca("Toyota")                        // nuevo en SB-04
                .precio(new BigDecimal("185.00")).stock(42)
                .build();
    }

    @Test
    @DisplayName("toResponse incluye el campo marca")
    void toResponse_incluyeMarca() {
        when(repositorio.findById(1L)).thenReturn(Optional.of(repuesto));
        RepuestoResponse r = servicio.buscarPorId(1L);
        assertThat(r.getMarca()).isEqualTo("Toyota");
    }

    @Test
    @DisplayName("crear persiste marca correctamente")
    void crear_persisteMarca() {
        when(repositorio.existsByCodigo("EMP-HONDA-002")).thenReturn(false);
        when(repositorio.save(any())).thenAnswer(inv -> {
            Repuesto r = inv.getArgument(0);
            r.setId(5L);
            return r;
        });

        RepuestoRequest req = new RepuestoRequest();
        req.setCodigo("EMP-HONDA-002"); req.setDescripcion("Empaquetadura Honda");
        req.setDivision("EMPAQUETADURAS"); req.setMarca("Honda");
        req.setPrecio(new BigDecimal("210.00")); req.setStock(10);

        RepuestoResponse resp = servicio.crear(req);
        assertThat(resp.getMarca()).isEqualTo("Honda");
    }

    @Test
    @DisplayName("buscarPorId lanza excepción si no existe")
    void buscarPorId_lanzaExcepcion() {
        when(repositorio.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> servicio.buscarPorId(99L))
                .isInstanceOf(RepuestoNotFoundException.class);
    }
}
