package pe.com.pevisa.genericos;


import java.util.*;
import java.util.function.BiFunction;
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

        IO.println("--- diamante y var");
        Map<String, List<Integer>> notas = new HashMap<>(); // Java 7
        var mapa = new TreeMap<String, List<Integer>>();
        mapa.put("Pevisa", List.of(20, 18, 19));

        var lista = new ArrayList<>(); // ArrayList<Object>
        lista.add("Pevisa");
        lista.add(1);

        Comparator<String> cmp = (s1, s2) -> s1.length() - s2.length();

        IO.println(cmp.compare("a", "abc"));


        BiFunction<Integer, Integer, Integer> suma = (var a, var b) -> a + b; // var en lambdas (java 11)
        IO.println("suma = " + suma.apply(3, 4));








    }
}
