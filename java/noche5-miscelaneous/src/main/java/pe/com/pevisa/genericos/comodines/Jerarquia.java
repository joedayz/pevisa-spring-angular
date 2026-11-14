package pe.com.pevisa.genericos.comodines;

import java.util.ArrayList;
import java.util.List;

public class Jerarquia {

    static void main() {
        Producto[] productos = new Comida[10];
        productos[0] = new Comida("Arroz", 10.0);
        IO.println(productos[0].nombre());

        try{
            productos[1] = new Bebida("Coca Cola", 5.0);
        }   catch (ArrayStoreException e) {
            IO.println("No se puede almacenar una Bebida en un arreglo de Comida");
        }

        //Los genericos son invariantes, es decir, no se puede asignar un List<Comida> a un List<Producto>
        //List<Producto> lista = new ArrayList<Comida>();;;
        List<? extends Producto> lista = new ArrayList<Comida>(); // con comodin si te dejo
        IO.println("List<? extends Producto> acepta un ArrayList<Comida> y un ArrayList<Bebida>: " + lista.isEmpty());


    }
}
