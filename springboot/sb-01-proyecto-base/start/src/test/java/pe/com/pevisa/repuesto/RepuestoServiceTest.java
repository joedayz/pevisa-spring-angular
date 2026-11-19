package pe.com.pevisa.repuesto;


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
import pe.com.pevisa.repuesto.service.RepuestoService;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RepuestoServiceTest {

    private Repuesto repuestoEjemplo;

    @Mock
    private RepuestoRepository repositorio;

    @InjectMocks
    private RepuestoService servicio;

    @BeforeEach
    void setUp(){
        servicio = new RepuestoService(repositorio);
        repuestoEjemplo = Repuesto.builder()
                .id(1L)
                .codigo("EMP-TOYOTA-001")
                .descripcion("Empaquetadura de motor Toyota Corolla 1.8")
                .division("EMPAQUETADURAS")
                .precio(new BigDecimal("185.00"))
                .stock(42)
                .build();
    }

    @Test
    @DisplayName("buscarPorId devuelve el repuesto cuando existe")
    void buscarPorId_existente(){
        when(repositorio.findById(1L)).thenReturn(Optional.of(repuestoEjemplo));

        RepuestoResponse repuesta = servicio.buscarPorId(1L); // internamente llama al repositorio.findById(1L)

        assertThat(repuesta.codigo()).isEqualTo("EMP-TOYOTA-001");
    }


    @Test
    @DisplayName("buscarPorId lanza excepcion cuando no existe")
    void buscarPorId_noExistente(){
        when(repositorio.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> servicio.buscarPorId(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("99");
    }
}
