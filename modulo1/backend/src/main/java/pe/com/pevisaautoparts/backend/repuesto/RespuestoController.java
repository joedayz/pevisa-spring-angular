package pe.com.pevisaautoparts.backend.repuesto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/repuestos") // http://localhost:8080/api/repuestos
public class RespuestoController {

    private final RepuestoService repuestoService;

    public RespuestoController(RepuestoService repuestoService) {
        this.repuestoService = repuestoService;
    }

    @GetMapping // http://localhost:8080/api/repuestos GET
    public List<Repuesto> listar() {
        return repuestoService.listar();
    }

    @GetMapping("/{id}") // http://localhost:8080/api/repuestos/{id} GET
    public Repuesto buscarPorId(@PathVariable Long id) {
        return repuestoService.buscarPorId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No existe el respuesto con id: " + id));
    }
}
