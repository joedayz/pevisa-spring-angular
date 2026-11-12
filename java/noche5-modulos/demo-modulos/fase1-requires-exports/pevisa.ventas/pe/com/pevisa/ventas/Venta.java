package pe.com.pevisa.ventas;

import pe.com.pevisa.stock.Almacen;
import pe.com.pevisa.stock.Producto;
import pe.com.pevisa.stock.interno.Auditoria;

public class Venta {

    public void vender(Almacen almacen, Producto producto) {
        // pevisa.ventas SI puede usar pe.com.pevisa.stock.interno porque pevisa.stock
        // hizo "exports pe.com.pevisa.stock.interno to pevisa.ventas".
        Auditoria.registrar("Se vendio " + producto);
        System.out.println("Venta registrada desde almacen: " + almacen.getDireccion());
    }
}
