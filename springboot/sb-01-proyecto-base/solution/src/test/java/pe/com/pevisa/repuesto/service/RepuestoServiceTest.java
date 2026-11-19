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
import pe.com.pevisa.repuesto.repository.RepuestoRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test unitario del Service.
 *
 * @ExtendWith(MockitoExtension.class): activa Mockito sin levantar Spring.
 * @Mock: crea un doble del repositorio — no toca memoria ni BD.
 * @InjectMocks: crea el Service e inyecta los mocks.
 *
 * Estos tests son rápidos (< 50ms) porque no arrancan el contexto.
 * En SB-11 agregamos tests de integración con @SpringBootTest.
 */
@ExtendWith(MockitoExtension.class)
class RepuestoServiceTest {

    @Mock
    private RepuestoRepository repositorio;

    @InjectMocks
    private RepuestoService servicio;

    private Repuesto repuestoEjemplo;

    @BeforeEach
    void setUp() {
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
    void buscarPorId_existente() {
        when(repositorio.findById(1L)).thenReturn(Optional.of(repuestoEjemplo));

        RepuestoResponse respuesta = servicio.buscarPorId(1L);

        assertThat(respuesta.getCodigo()).isEqualTo("EMP-TOYOTA-001");
        assertThat(respuesta.getEstadoStock()).isEqualTo("NORMAL");
    }

    @Test
    @DisplayName("buscarPorId lanza excepción cuando no existe")
    void buscarPorId_noExistente() {
        when(repositorio.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> servicio.buscarPorId(99L))
                .isInstanceOf(RepuestoNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("crear guarda el repuesto y devuelve el id generado")
    void crear_repuestoNuevo() {
        when(repositorio.findByCodigo("FIL-BOSCH-002")).thenReturn(Optional.empty());
        when(repositorio.save(any())).thenReturn(
                Repuesto.builder().id(5L).codigo("FIL-BOSCH-002")
                        .descripcion("Filtro").division("BOSCH")
                        .precio(new BigDecimal("28.50")).stock(0).build()
        );

        RepuestoRequest request = new RepuestoRequest();
        request.setCodigo("FIL-BOSCH-002");
        request.setDescripcion("Filtro");
        request.setDivision("BOSCH");
        request.setPrecio(new BigDecimal("28.50"));
        request.setStock(0);

        RepuestoResponse respuesta = servicio.crear(request);

        assertThat(respuesta.getId()).isEqualTo(5L);
        assertThat(respuesta.getEstadoStock()).isEqualTo("AGOTADO");
        verify(repositorio, times(1)).save(any());
    }

    @Test
    @DisplayName("crear lanza excepción si el código ya existe")
    void crear_codigoDuplicado() {
        when(repositorio.findByCodigo("EMP-TOYOTA-001"))
                .thenReturn(Optional.of(repuestoEjemplo));

        RepuestoRequest request = new RepuestoRequest();
        request.setCodigo("EMP-TOYOTA-001");
        request.setDescripcion("Otro");
        request.setDivision("EMPAQUETADURAS");
        request.setPrecio(new BigDecimal("100.00"));
        request.setStock(10);

        assertThatThrownBy(() -> servicio.crear(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("EMP-TOYOTA-001");

        verify(repositorio, never()).save(any());
    }
}
