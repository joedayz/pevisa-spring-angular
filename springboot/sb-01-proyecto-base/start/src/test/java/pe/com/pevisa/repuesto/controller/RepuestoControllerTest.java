package pe.com.pevisa.repuesto.controller;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import pe.com.pevisa.repuesto.dto.RepuestoRequest;
import pe.com.pevisa.repuesto.dto.RepuestoResponse;
import pe.com.pevisa.repuesto.exception.RepuestoNotFoundException;
import pe.com.pevisa.repuesto.service.RepuestoService;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RepuestoController.class)
public class RepuestoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RepuestoService servicio;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/v1/repuestos devuelve lista con 200")
    void listar_devuelve200() throws Exception {

        RepuestoResponse r = new RepuestoResponse(
                1L,
                "EMP-TOYOTA-001",
                "Empaquetadura de motor Toyota Corolla 1.8",
                "EMPAQUETADURAS",
                new BigDecimal("185.00"),
                42
        );

        when(servicio.listar()).thenReturn(List.of(r));

        mockMvc.perform(get("/api/v1/repuestos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].codigo").value("EMP-TOYOTA-001"))
                .andExpect(jsonPath("$[0].descripcion").value("Empaquetadura de motor Toyota Corolla 1.8"))
                .andExpect(jsonPath("$[0].division").value("EMPAQUETADURAS"))
                .andExpect(jsonPath("$[0].precio").value(185.00))
                .andExpect(jsonPath("$[0].stock").value(42));

    }

    @Test
    @DisplayName("GET /api/v1/repuestos?division=BOSCH devuelve lista con 200")
    void listar_byDivision200() throws Exception {

        RepuestoResponse r = new RepuestoResponse(
                1L,
                "FIL-BOSCH-002",
                "Filtro de aceite Bosch universal",
                "BOSCH",
                new BigDecimal("28.50"),
                150
        );

        when(servicio.buscarPorDivision("BOSCH")).thenReturn(List.of(r));

        mockMvc.perform(get("/api/v1/repuestos?division=BOSCH"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].codigo").value("FIL-BOSCH-002"))
                .andExpect(jsonPath("$[0].descripcion").value("Filtro de aceite Bosch universal"))
                .andExpect(jsonPath("$[0].division").value("BOSCH"))
                .andExpect(jsonPath("$[0].precio").value(28.50))
                .andExpect(jsonPath("$[0].stock").value(150));

    }

    @Test
    @DisplayName("GET /api/v1/repuestos/{id} devuelve 404 cuando no existe")
    void buscarPorId_noExiste_devuelve404() throws Exception {
        when(servicio.buscarPorId(99L)).thenThrow(new RepuestoNotFoundException(99L));

        mockMvc.perform(get("/api/v1/repuestos/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Repuesto no encontrado"));

    }

    @Test
    @DisplayName("POST /api/v1/repuestos con body inválido devuelve 400")
    void crear_bodyInvalido_devuelve400() throws Exception {
        RepuestoRequest request = new RepuestoRequest(
                "",
                "",
                "",
                new BigDecimal("0"),
                0
        );


        mockMvc.perform(post("/api/v1/repuestos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Error de validación"));
    }

    @Test
    @DisplayName("POST /api/v1/repuestos crea y devuelve 201")
    void crear_valido_devuelve201() throws Exception {

        RepuestoRequest request = new RepuestoRequest(
                "BAT-VOLTA-010",
                "Batería Volta 70Ah",
                "BATERIAS",
                new BigDecimal("380.00"),
                15
        );

        RepuestoResponse response = new RepuestoResponse(
                10L,
                "BAT-VOLTA-010",
                "Batería Volta 70Ah",
                "BATERIAS",
                new BigDecimal("380.00"),
                15
        );


        when(servicio.crear(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/repuestos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.estadoStock").value("DISPONIBLE"));


    }
}
