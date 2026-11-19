package pe.com.pevisa.repuesto.exception;

public class PedidoNotFoundException extends RuntimeException {
    public PedidoNotFoundException(String numeroPedido) {
        super("No se encontró el pedido con número: " + numeroPedido);
    }
}
