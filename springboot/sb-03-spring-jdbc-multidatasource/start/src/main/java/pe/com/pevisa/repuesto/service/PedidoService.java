package pe.com.pevisa.repuesto.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.com.pevisa.repuesto.dto.PedidoResponse;
import pe.com.pevisa.repuesto.dto.ResumenVentasResponse;
import pe.com.pevisa.repuesto.exception.PedidoNotFoundException;
import pe.com.pevisa.repuesto.model.Pedido;
import pe.com.pevisa.repuesto.repository.jdbc.PedidoLegadoRepository;

import java.util.List;

/**
 * TODO 16: Implementar listarTodos(), buscarPorNumero(), buscarPorRepuesto(),
 *          buscarPorEstado(), obtenerResumenVentas().
 *
 *          buscarPorNumero() debe lanzar PedidoNotFoundException si no existe.
 *
 *          obtenerResumenVentas() debe devolver un ResumenVentasResponse con ceros
 *          si el procedimiento no retorna datos (Optional.empty()).
 *
 * TODO 17: Implementar toResponse(Pedido p) → PedidoResponse usando el builder.
 *
 * Pregunta: ¿por qué este Service NO tiene @Transactional?
 *           ¿Qué necesitaríamos agregar si quisiéramos transacciones sobre el legado?
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoLegadoRepository pedidoRepo;

    public List<PedidoResponse> listarTodos() {
        // TODO 16a
        return List.of();
    }

    public PedidoResponse buscarPorNumero(String numeroPedido) {
        // TODO 16b: orElseThrow con PedidoNotFoundException
        return null;
    }

    public List<PedidoResponse> buscarPorRepuesto(String codigoRepuesto) {
        // TODO 16c
        return List.of();
    }

    public List<PedidoResponse> buscarPorEstado(String estado) {
        // TODO 16d
        return List.of();
    }

    public ResumenVentasResponse obtenerResumenVentas(String codigoRepuesto) {
        // TODO 16e: si Optional.empty() devolver ResumenVentasResponse con ceros
        return null;
    }

    private PedidoResponse toResponse(Pedido p) {
        // TODO 17
        return null;
    }
}
