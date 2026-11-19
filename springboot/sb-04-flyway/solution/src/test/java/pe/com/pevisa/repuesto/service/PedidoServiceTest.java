package pe.com.pevisa.repuesto.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.com.pevisa.repuesto.dto.PedidoResponse;
import pe.com.pevisa.repuesto.exception.PedidoNotFoundException;
import pe.com.pevisa.repuesto.model.Pedido;
import pe.com.pevisa.repuesto.repository.jdbc.PedidoLegadoRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock  PedidoLegadoRepository pedidoRepo;
    @InjectMocks PedidoService servicio;

    private Pedido pedidoEjemplo() {
        return Pedido.builder()
                .id(1L).numeroPedido("PED-001")
                .codigoRepuesto("EMP-TOYOTA-001")
                .distribuidor("Distribuidora Lima")
                .cantidad(10).precioUnitario(new BigDecimal("185.00"))
                .total(new BigDecimal("1850.00"))
                .estado("ENTREGADO")
                .fechaPedido(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("buscarPorNumero devuelve response cuando existe")
    void buscarPorNumero_existe() {
        when(pedidoRepo.findByNumeroPedido("PED-001")).thenReturn(Optional.of(pedidoEjemplo()));
        PedidoResponse r = servicio.buscarPorNumero("PED-001");
        assertThat(r.getNumeroPedido()).isEqualTo("PED-001");
        assertThat(r.getTotal()).isEqualByComparingTo("1850.00");
    }

    @Test
    @DisplayName("buscarPorNumero lanza PedidoNotFoundException cuando no existe")
    void buscarPorNumero_noExiste() {
        when(pedidoRepo.findByNumeroPedido("XXX")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> servicio.buscarPorNumero("XXX"))
                .isInstanceOf(PedidoNotFoundException.class)
                .hasMessageContaining("XXX");
    }

    @Test
    @DisplayName("buscarPorRepuesto delega al repositorio correctamente")
    void buscarPorRepuesto() {
        when(pedidoRepo.findByCodigoRepuesto("EMP-TOYOTA-001"))
                .thenReturn(List.of(pedidoEjemplo(), pedidoEjemplo()));
        List<PedidoResponse> resultado = servicio.buscarPorRepuesto("EMP-TOYOTA-001");
        assertThat(resultado).hasSize(2);
        verify(pedidoRepo).findByCodigoRepuesto("EMP-TOYOTA-001");
    }

    @Test
    @DisplayName("obtenerResumenVentas devuelve vacío con ceros cuando no hay datos")
    void obtenerResumenVentas_sinDatos() {
        when(pedidoRepo.resumenVentas("XXX-000")).thenReturn(Optional.empty());
        var resumen = servicio.obtenerResumenVentas("XXX-000");
        assertThat(resumen.getTotalUnidades()).isEqualTo(0L);
        assertThat(resumen.getNumPedidos()).isEqualTo(0L);
    }
}
