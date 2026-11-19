package pe.com.pevisa.repuesto.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pe.com.pevisa.repuesto.dto.PedidoResponse;
import pe.com.pevisa.repuesto.dto.ResumenVentasResponse;
import pe.com.pevisa.repuesto.service.PedidoService;

import java.util.List;

/**
 * Endpoints del datasource legado.
 * Son solo lectura — el legado no recibe escrituras desde esta app.
 *
 * Base path: /api/v1/pedidos
 */
@RestController
@RequestMapping("/api/v1/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService servicio;

    /** GET /api/v1/pedidos */
    @GetMapping
    public List<PedidoResponse> listar() {
        return servicio.listarTodos();
    }

    /** GET /api/v1/pedidos/PED-2024-0001 */
    @GetMapping("/{numeroPedido}")
    public PedidoResponse buscarPorNumero(@PathVariable String numeroPedido) {
        return servicio.buscarPorNumero(numeroPedido);
    }

    /** GET /api/v1/pedidos?estado=PENDIENTE */
    @GetMapping(params = "estado")
    public List<PedidoResponse> porEstado(@RequestParam String estado) {
        return servicio.buscarPorEstado(estado);
    }

    /**
     * GET /api/v1/pedidos/repuesto/EMP-TOYOTA-001
     * Historial de pedidos de un repuesto específico.
     */
    @GetMapping("/repuesto/{codigo}")
    public List<PedidoResponse> porRepuesto(@PathVariable String codigo) {
        return servicio.buscarPorRepuesto(codigo);
    }

    /**
     * GET /api/v1/pedidos/repuesto/EMP-TOYOTA-001/resumen
     * Resumen de ventas via procedimiento almacenado.
     */
    @GetMapping("/repuesto/{codigo}/resumen")
    public ResumenVentasResponse resumenVentas(@PathVariable String codigo) {
        return servicio.obtenerResumenVentas(codigo);
    }
}
