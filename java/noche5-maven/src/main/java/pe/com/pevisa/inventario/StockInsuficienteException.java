package pe.com.pevisa.inventario;

public class StockInsuficienteException extends RuntimeException{
    public StockInsuficienteException(String codigo, int cantidad, int disponible) {
        super("Stock insuficiente para el producto " + codigo + ": se intentó retirar " + cantidad + " pero solo hay " + disponible + " disponibles.");
    }
}
