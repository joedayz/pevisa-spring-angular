package pe.com.pevisa.genericos.comodines;

import java.util.ArrayList;
import java.util.List;

public class Comodines {

    // invariante: solo se puede recibir un List<Producto>
    static void setProductos(List<Producto> productos){
        IO.println(" setProductos: " + productos.size());
    }

    // ? extends: covariante, se puede recibir un List<Comida> o un List<Bebida> o un List<Producto>
    static double total(List<? extends Producto> productos){
        double total = 0.0;
        for (Producto producto : productos) {
            total += producto.precio();
        }
        return total;
    }


    // ? super: contravariante, se puede recibir un List<Producto> , List<Comida>,  List<Object>
    static void agregarComida(List<? super Comida> pedido, Comida c){
        pedido.add(c);
        Object o = pedido.get(0);
    }

    // ?: tipo desconocido, se lee como Object
    static void imprimir(List<?> lista){
        for (Object o : lista) {
            IO.println(o);
        }

        //lista.add("X"); // no se puede agregar un elemento de tipo desconocido
    }

    static void main() {

        Producto p1 = new Comida("Pastel", 2.99);
        Producto p2 = new Bebida("Té", 1.99);
        Producto p3 = new Comida("Galleta", 2.99);
        List<Producto> productos = List.of(p1, p2, p3);
        List<Comida> comidas = List.of((Comida) p1, (Comida) p3);

        IO.println("--- invariante");
        setProductos(productos);


        IO.println("--- ? extends Producto");
        IO.println("  total productos = %.2f".formatted(total(productos)));
        IO.println("  total comidas   = %.2f".formatted(total(comidas)));

        IO.println("--- ? super Comida");
        List<Object> objetos = new ArrayList<>();
        List<Producto> pedido = new ArrayList<>();
        List<Comida> soloComida = new ArrayList<>();
        Comida pan = new Comida("Pan", 0.5);

        agregarComida(objetos, pan);
        agregarComida(pedido, pan);
        agregarComida(soloComida, pan);


        IO.println("--- ?");
        imprimir(comidas);
        imprimir(objetos);
        imprimir(productos);



    }
}
