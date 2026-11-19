package pe.com.pevisa.repuesto.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pe.com.pevisa.repuesto.dto.PageResponse;
import pe.com.pevisa.repuesto.dto.RepuestoResponse;
import pe.com.pevisa.repuesto.service.RepuestoService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RepuestoController.class)
class RepuestoControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @MockBean  RepuestoService servicio;

    @Test
    @DisplayName("GET /paginado devuelve metadata de paginación")
    void listarPaginado() throws Exception {
        RepuestoResponse r = RepuestoResponse.builder()
                .id(1L).codigo("EMP-TOYOTA-001").descripcion("Empaquetadura")
                .division("EMPAQUETADURAS").precio(new BigDecimal("185.00"))
                .stock(42).estadoStock("NORMAL").build();

        PageResponse<RepuestoResponse> page = PageResponse.<RepuestoResponse>builder()
                .contenido(List.of(r))
                .paginaActual(0).totalPaginas(3).totalElementos(25L)
                .tamanioPagina(10).ultima(false)
                .build();

        when(servicio.listarPaginado(0, 10, "codigo")).thenReturn(page);

        mockMvc.perform(get("/api/v1/repuestos/paginado"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElementos").value(25))
                .andExpect(jsonPath("$.totalPaginas").value(3))
                .andExpect(jsonPath("$.contenido[0].codigo").value("EMP-TOYOTA-001"));
    }

    @Test
    @DisplayName("GET /stock-bajo delega al service con umbral por defecto")
    void stockBajo() throws Exception {
        when(servicio.buscarConStockBajo(10)).thenReturn(List.of());
        mockMvc.perform(get("/api/v1/repuestos/stock-bajo"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /buscar?q=toyota delega la búsqueda")
    void buscar() throws Exception {
        when(servicio.buscar("toyota")).thenReturn(List.of());
        mockMvc.perform(get("/api/v1/repuestos/buscar?q=toyota"))
                .andExpect(status().isOk());
    }
}
