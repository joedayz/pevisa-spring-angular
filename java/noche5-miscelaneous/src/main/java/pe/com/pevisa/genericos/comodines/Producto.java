package pe.com.pevisa.genericos.comodines;

public sealed interface Producto permits Comida, Bebida {
    String nombre();
    double precio();
}
