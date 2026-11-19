package pe.com.pevisa.repuesto.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
@RequestMapping("/api/v1/repuestos") // http://localhost:8080/api/v1/repuestos
public class RepuestoController {

    @GetMapping // http://localhost:8080/api/v1/repuestos
    public String hello() {
        return "Hello Pevisa!";
    }
}
