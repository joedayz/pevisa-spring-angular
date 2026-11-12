package pe.com.pevisa.stock;

public class Producto {

    private final String nombre;
    private final int cantidad;

    public Producto(String nombre, int cantidad) {
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    @Override
    public String toString() {
        return nombre + " (" + cantidad + " unidades)";
    }
}
