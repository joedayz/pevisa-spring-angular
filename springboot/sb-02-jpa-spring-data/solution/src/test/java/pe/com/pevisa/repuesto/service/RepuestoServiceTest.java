package pe.com.pevisa.repuesto.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import pe.com.pevisa.repuesto.dto.PageResponse;
import pe.com.pevisa.repuesto.dto.RepuestoRequest;
import pe.com.pevisa.repuesto.dto.RepuestoResponse;
import pe.com.pevisa.repuesto.exception.RepuestoNotFoundException;
import pe.com.pevisa.repuesto.model.Repuesto;
import pe.com.pevisa.repuesto.repository.RepuestoRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RepuestoServiceTest {

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
        assertThat(r.getCodigo()).isEqualTo("EMP-TOYOTA-001");
        assertThat(r.getEstadoStock()).isEqualTo("NORMAL");
    }

    @Test
    @DisplayName("buscarPorId lanza excepción cuando no existe")
    void buscarPorId_noExiste() {
        when(repositorio.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> servicio.buscarPorId(99L))
                .isInstanceOf(RepuestoNotFoundException.class);
    }

    @Test
    @DisplayName("crear verifica código duplicado con existsByCodigo")
    void crear_codigoDuplicado() {
        when(repositorio.existsByCodigo("EMP-TOYOTA-001")).thenReturn(true);

        RepuestoRequest req = new RepuestoRequest();
        req.setCodigo("EMP-TOYOTA-001");
        req.setDescripcion("Otro"); req.setDivision("X");
        req.setPrecio(BigDecimal.TEN); req.setStock(1);

        assertThatThrownBy(() -> servicio.crear(req))
                .isInstanceOf(IllegalArgumentException.class);
        verify(repositorio, never()).save(any());
    }

    @Test
    @DisplayName("listarPaginado devuelve PageResponse con metadata correcta")
    void listarPaginado() {
        Page<Repuesto> page = new PageImpl<>(
                List.of(repuesto),
                PageRequest.of(0, 10),
                25L  // total de elementos
        );
        when(repositorio.findAll(any(PageRequest.class))).thenReturn(page);

        PageResponse<RepuestoResponse> resp = servicio.listarPaginado(0, 10, "codigo");

        assertThat(resp.getTotalElementos()).isEqualTo(25L);
        assertThat(resp.getTotalPaginas()).isEqualTo(3);   // ceil(25/10)
        assertThat(resp.getPaginaActual()).isEqualTo(0);
        assertThat(resp.getContenido()).hasSize(1);
        assertThat(resp.isUltima()).isFalse();
    }

    @Test
    @DisplayName("estadoStock AGOTADO cuando stock es 0")
    void estadoStock_agotado() {
        repuesto.setStock(0);
        when(repositorio.findById(1L)).thenReturn(Optional.of(repuesto));
        assertThat(servicio.buscarPorId(1L).getEstadoStock()).isEqualTo("AGOTADO");
    }

    @Test
    @DisplayName("estadoStock ALTO cuando stock >= 50")
    void estadoStock_alto() {
        repuesto.setStock(100);
        when(repositorio.findById(1L)).thenReturn(Optional.of(repuesto));
        assertThat(servicio.buscarPorId(1L).getEstadoStock()).isEqualTo("ALTO");
    }
}
