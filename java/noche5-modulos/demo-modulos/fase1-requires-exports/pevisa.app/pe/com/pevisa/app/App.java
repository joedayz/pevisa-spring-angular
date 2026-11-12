package pe.com.pevisa.app;

import pe.com.pevisa.stock.Almacen;
import pe.com.pevisa.stock.Producto;
import pe.com.pevisa.ventas.Venta;
// import pe.com.pevisa.stock.interno.Auditoria; // <- descomenta esta linea para
// comprobar que pevisa.app NO puede verla: el export calificado solo
// autoriza a pevisa.ventas, aunque pevisa.app tenga acceso transitivo a pevisa.stock.

public class App {

    public static void main(String[] args) {
        Almacen almacen = new Almacen();
        Producto producto = new Producto("Laptop", 10);

        almacen.recibir(producto);

        Venta venta = new Venta();
        venta.vender(almacen, producto);
    }
}
