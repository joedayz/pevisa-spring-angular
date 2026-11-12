package pe.com.pevisa.stock.modelo;

public class ProductoDTO {

    // Campos privados a proposito: para leerlos por reflexion hace falta "opens".
    private final String nombre;
    private final double precio;

    public ProductoDTO(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }
}
