package pe.com.pevisa.inventario;

import java.util.HashMap;
import java.util.Map;

public class Inventario {

    private final Map<String, Integer> stock = new HashMap<>();

    public int stockDe(String codigo) {
        return stock.getOrDefault(codigo, 0);
    }

    public void ingresar(String codigo, int cantidad) {
        if(cantidad<=0){
            throw new IllegalArgumentException("La cantidad a ingresar debe ser mayor a cero");
        }
        stock.merge(codigo, cantidad, Integer::sum);
    }

    public void salida(String codigo, int cantidad){
        int disponible = stockDe(codigo);
        if(cantidad > disponible){
            throw  new StockInsuficienteException(codigo, cantidad, disponible);
        }
        stock.put(codigo, disponible - cantidad);
    }
}
