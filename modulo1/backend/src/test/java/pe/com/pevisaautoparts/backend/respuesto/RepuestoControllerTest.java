package pe.com.pevisaautoparts.backend.respuesto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RepuestoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void listaLosRepuestosDelCatalogo() throws Exception{
        mockMvc.perform(get("/api/repuestos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].codigo").value("FLT-001"))
                .andExpect(jsonPath("$.length()").value(8));
    }


    @Test
    void buscarUnRepuestoPorId() throws Exception{
        mockMvc.perform(get("/api/repuestos/7"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigo").value("ELE-017"))
                .andExpect(jsonPath("$.nombre").value("Batería 12V 75Ah"));
    }


    @Test
    void response404CuandoElRepuestoNoExiste() throws Exception{
        mockMvc.perform(get("/api/repuestos/999"))
                .andExpect(status().isNotFound());
    }
}
