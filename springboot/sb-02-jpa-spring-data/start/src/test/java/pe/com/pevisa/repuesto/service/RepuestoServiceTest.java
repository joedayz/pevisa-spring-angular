package pe.com.pevisa.repuesto.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.com.pevisa.repuesto.dto.RepuestoResponse;
import pe.com.pevisa.repuesto.model.Repuesto;
import pe.com.pevisa.repuesto.repository.RepuestoRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RepuestoServiceTest {

    @Mock
    private RepuestoRepository repositorio;

    @InjectMocks
    private RepuestoService servicio;

    private Repuesto repuesto;

    @BeforeEach
    void setUp() {
        repuesto = Repuesto.builder()
                .id(1L)
                .codigo("EMP-TOYOTA-001")
                .descripcion("Empaquetadura Toyota Corolla 1.8")
                .division("EMPAQUETADURAS")
                .precio(new BigDecimal("185.00"))
                .stock(42)
                .build();
    }

    @Test
    @DisplayName("buscarPorId devuelve response cuando existe")
    void buscarPorId_existe() {

        when(repositorio.findById(1L)).thenReturn(Optional.of(repuesto));

        RepuestoResponse r = servicio.buscarPorId(1L);

        assertThat(r.codigo()).isEqualTo("EMP-TOYOTA-001");;
        assertThat(r.estadoStock()).isEqualTo("DISPONIBLE");
    }
}
