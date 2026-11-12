package pe.com.pevisa.stock;

public class Almacen {

    private final String direccion = "Callao Av. Elmer Faucett 1234";

    public String getDireccion() {
        return direccion;
    }

    public void recibir(Producto producto) {
        System.out.println("Almacen [" + direccion + "] recibio: " + producto);
    }
}
