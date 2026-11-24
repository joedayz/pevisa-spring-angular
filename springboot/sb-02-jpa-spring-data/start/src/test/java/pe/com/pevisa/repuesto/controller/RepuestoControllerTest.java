package pe.com.pevisa.repuesto.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import pe.com.pevisa.repuesto.service.RepuestoService;
import tools.jackson.databind.ObjectMapper;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RepuestoController.class)
public class RepuestoControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockitoBean
    RepuestoService servicio;

    @Test
    @DisplayName("GET /stock-bajo delega al service con umbral por defecto")
    void stockBajo() throws Exception {
        when(servicio.buscarConStockBajo(10)).thenReturn(List.of());
        mockMvc.perform(get("/api/v1/repuestos/stock-bajo"))
                .andExpect(status().isOk());
    }

}
