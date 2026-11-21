package pe.com.pevisa.repuesto.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.com.pevisa.repuesto.dto.RepuestoResponse;
import pe.com.pevisa.repuesto.service.RepuestoService;

import java.util.List;


/**
 * Capa de presentación — transforma HTTP en llamadas al Service.
 *
 * Reglas de la capa:
 *  - Solo habla HTTP: recibe requests, devuelve ResponseEntity.
 *  - No contiene lógica de negocio (ni un if de reglas de dominio).
 *  - Delega todo al Service.
 *
 * @RestController = @Controller + @ResponseBody
 *   Cada método devuelve el objeto directamente serializado a JSON.
 *
 * @RequestMapping("/api/v1/repuestos")
 *   Versionamos la URL desde el inicio. Cuando necesitemos romper el
 *   contrato, creamos /api/v2/… sin romper a los clientes del v1.
 */

@RestController
@RequestMapping("/api/v1/repuestos")
@RequiredArgsConstructor// http://localhost:8080/api/v1/repuestos
public class RepuestoController {

    private final RepuestoService servicio;


    @GetMapping // http://localhost:8080/api/v1/repuestos o /api/v1/repuestos?division=BOSCH
    public List<RepuestoResponse> listar( @RequestParam(required = false) String division) {
        if(division != null && !division.isBlank()){
            return servicio.buscarPorDivision(division);
        }
        return servicio.listar();
    }


}
