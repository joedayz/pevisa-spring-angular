package pe.com.pevisa.genericos;


import java.util.List;
import java.util.function.Function;

class Caja<T>{
    private T valor;
    Caja(T valor){
        this.valor  = valor;
    }
    T obtener(){
        return valor;
    }
    void poner(T valor){
        this.valor = valor;
    }
    //metodo generico de instancia: declara su propio parametro R
    <R> Caja<R> map(Function<? super T, ? extends R> f) { return new Caja<>(f.apply(valor)); }

    @Override public String toString() { return "Caja[" + valor + "]"; }
}

interface Par<K, V> {
    K clave();
    V valor();
}

record Entrada<K, V>(K clave, V valor) implements Par<K, V> { }


class Utiles{

    //metodo static
    static <T> T primero(List<T> lista) { return lista.getFirst(); }

    /// Tipo acotado: solo acepta tipos comparables
    static <T extends Comparable<? super T>> T mayor(List<T> lista) {
        T max = lista.getFirst();
        for (T t : lista) if (t.compareTo(max) > 0) max = t;
        return max;
    }
}



public class Fundamentos {


    static void main() {
        Caja<String> c = new Caja<>("Pevisa");
        System.out.println(c);
        String s = c.obtener();
        System.out.println(s);
        Caja<Integer> largo = c.map(String::length);
        System.out.println(largo);


        Par<String, Integer> p = new Entrada<>("Pevisa", 2024);
        System.out.println(p.clave() + " -> " + p.valor());

        List<String> lenguajes = List.of("Java", "Kotlin", "Scala");
        String uno = Utiles.primero(lenguajes);
        String dos = Utiles.<String>primero(lenguajes);
        System.out.println(uno + ", " + dos);

        IO.println("mayor entero = " + Utiles.mayor(List.of(3, 9, 4)));
    }
}
