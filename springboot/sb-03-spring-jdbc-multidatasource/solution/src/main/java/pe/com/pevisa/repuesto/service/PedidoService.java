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
 * Service del datasource legado.
 *
 * SIN @Transactional de JPA — no hay EntityManager para el legado.
 * Si necesitáramos transacciones en el legado, usaríamos
 * @Transactional("legadoTransactionManager") con un DataSourceTransactionManager
 * adicional. Por ahora el legado es solo lectura.
 *
 * NOTA sobre transacciones distribuidas (XA):
 * Si necesitaras atomicidad entre AMBOS datasources (escribir en Postgres
 * y en el legado en la misma transacción), necesitarías un XA TransactionManager
 * (Atomikos, Bitronix) o el patrón Saga para microservicios.
 * Para PEVISA la separación es clara: el legado es solo consulta.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoLegadoRepository pedidoRepo;

    public List<PedidoResponse> listarTodos() {
        log.info("Listando pedidos del datasource legado");
        return pedidoRepo.findAll().stream().map(this::toResponse).toList();
    }

    public PedidoResponse buscarPorNumero(String numeroPedido) {
        return pedidoRepo.findByNumeroPedido(numeroPedido)
                .map(this::toResponse)
                .orElseThrow(() -> new PedidoNotFoundException(numeroPedido));
    }

    public List<PedidoResponse> buscarPorRepuesto(String codigoRepuesto) {
        log.info("Buscando pedidos del repuesto={}", codigoRepuesto);
        return pedidoRepo.findByCodigoRepuesto(codigoRepuesto)
                .stream().map(this::toResponse).toList();
    }

    public List<PedidoResponse> buscarPorEstado(String estado) {
        return pedidoRepo.findByEstado(estado)
                .stream().map(this::toResponse).toList();
    }

    /**
     * Caso de uso que cruza ambos datasources:
     * consulta el catálogo (JPA) para validar que el repuesto existe
     * y el legado para traer sus ventas históricas.
     *
     * Esto demuestra que un Service puede coordinar múltiples repositorios
     * de distintas fuentes de datos sin acoplarse a la tecnología de cada uno.
     */
    public ResumenVentasResponse obtenerResumenVentas(String codigoRepuesto) {
        log.info("Obteniendo resumen de ventas para repuesto={}", codigoRepuesto);
        return pedidoRepo.resumenVentas(codigoRepuesto)
                .orElse(ResumenVentasResponse.builder()
                        .codigoRepuesto(codigoRepuesto)
                        .totalUnidades(0L)
                        .numPedidos(0L)
                        .build());
    }

    private PedidoResponse toResponse(Pedido p) {
        return PedidoResponse.builder()
                .id(p.getId())
                .numeroPedido(p.getNumeroPedido())
                .codigoRepuesto(p.getCodigoRepuesto())
                .distribuidor(p.getDistribuidor())
                .cantidad(p.getCantidad())
                .precioUnitario(p.getPrecioUnitario())
                .total(p.getTotal())
                .estado(p.getEstado())
                .fechaPedido(p.getFechaPedido())
                .build();
    }
}
