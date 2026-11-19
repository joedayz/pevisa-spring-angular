package pe.com.pevisa.repuesto.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pe.com.pevisa.repuesto.dto.RepuestoRequest;
import pe.com.pevisa.repuesto.dto.RepuestoResponse;
import pe.com.pevisa.repuesto.exception.RepuestoNotFoundException;
import pe.com.pevisa.repuesto.service.RepuestoService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test de slice del Controller.
 *
 * @WebMvcTest levanta solo la capa web (filtros, controllers, advice).
 * No levanta el contexto completo ni la BD — es rápido.
 *
 * MockMvc simula llamadas HTTP sin abrir un puerto real.
 * @MockBean reemplaza el Service con un mock de Mockito dentro del contexto Spring.
 */
@WebMvcTest(RepuestoController.class)
class RepuestoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RepuestoService servicio;

    @Test
    @DisplayName("GET /api/v1/repuestos devuelve lista con 200")
    void listar_devuelve200() throws Exception {
        RepuestoResponse r = RepuestoResponse.builder()
                .id(1L).codigo("EMP-TOYOTA-001")
                .descripcion("Empaquetadura Toyota").division("EMPAQUETADURAS")
                .precio(new BigDecimal("185.00")).stock(42).estadoStock("NORMAL")
                .build();

        when(servicio.listar()).thenReturn(List.of(r));

        mockMvc.perform(get("/api/v1/repuestos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].codigo").value("EMP-TOYOTA-001"))
                .andExpect(jsonPath("$[0].estadoStock").value("NORMAL"));
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
        RepuestoRequest request = new RepuestoRequest();
        // codigo en blanco — viola @NotBlank

        mockMvc.perform(post("/api/v1/repuestos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Error de validación"));
    }

    @Test
    @DisplayName("POST /api/v1/repuestos crea y devuelve 201")
    void crear_valido_devuelve201() throws Exception {
        RepuestoRequest request = new RepuestoRequest();
        request.setCodigo("BAT-VOLTA-010");
        request.setDescripcion("Batería Volta 70Ah");
        request.setDivision("BATERIAS");
        request.setPrecio(new BigDecimal("380.00"));
        request.setStock(15);

        RepuestoResponse response = RepuestoResponse.builder()
                .id(10L).codigo("BAT-VOLTA-010")
                .descripcion("Batería Volta 70Ah").division("BATERIAS")
                .precio(new BigDecimal("380.00")).stock(15).estadoStock("NORMAL")
                .build();

        when(servicio.crear(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/repuestos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.estadoStock").value("NORMAL"));
    }
}
