package pe.com.pevisa.repuesto.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pe.com.pevisa.repuesto.dto.PedidoResponse;
import pe.com.pevisa.repuesto.dto.ResumenVentasResponse;
import pe.com.pevisa.repuesto.exception.PedidoNotFoundException;
import pe.com.pevisa.repuesto.service.PedidoService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PedidoController.class)
class PedidoControllerTest {

    @Autowired MockMvc mockMvc;
    @MockBean  PedidoService servicio;

    private PedidoResponse pedidoEjemplo() {
        return PedidoResponse.builder()
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
    @DisplayName("GET /api/v1/pedidos devuelve lista")
    void listar() throws Exception {
        when(servicio.listarTodos()).thenReturn(List.of(pedidoEjemplo()));
        mockMvc.perform(get("/api/v1/pedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].numeroPedido").value("PED-001"))
                .andExpect(jsonPath("$[0].estado").value("ENTREGADO"));
    }

    @Test
    @DisplayName("GET /api/v1/pedidos/{numero} devuelve 404 si no existe")
    void buscarPorNumero_notFound() throws Exception {
        when(servicio.buscarPorNumero("XXX")).thenThrow(new PedidoNotFoundException("XXX"));
        mockMvc.perform(get("/api/v1/pedidos/XXX"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Pedido no encontrado"));
    }

    @Test
    @DisplayName("GET /api/v1/pedidos/repuesto/{codigo}/resumen devuelve resumen de ventas")
    void resumenVentas() throws Exception {
        when(servicio.obtenerResumenVentas("EMP-TOYOTA-001")).thenReturn(
                ResumenVentasResponse.builder()
                        .codigoRepuesto("EMP-TOYOTA-001")
                        .totalUnidades(30L)
                        .totalIngresos(new BigDecimal("5550.00"))
                        .numPedidos(2L)
                        .build()
        );
        mockMvc.perform(get("/api/v1/pedidos/repuesto/EMP-TOYOTA-001/resumen"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalUnidades").value(30))
                .andExpect(jsonPath("$.numPedidos").value(2));
    }
}
