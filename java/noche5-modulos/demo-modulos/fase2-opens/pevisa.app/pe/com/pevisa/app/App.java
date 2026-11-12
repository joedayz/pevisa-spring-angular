package pe.com.pevisa.app;

import pe.com.pevisa.framework.Mapper;
import pe.com.pevisa.stock.modelo.ProductoDTO;

public class App {

    public static void main(String[] args) {
        ProductoDTO producto = new ProductoDTO("Laptop", 2999.90);

        // pevisa.app puede construir ProductoDTO porque el paquete esta "exports" normal.
        // pevisa.framework puede leer sus campos privados por reflexion porque
        // pevisa.stock hizo "opens pe.com.pevisa.stock.modelo to pevisa.framework".
        String json = Mapper.toJson(producto);
        System.out.println(json);
    }
}
