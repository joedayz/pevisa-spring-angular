package pe.com.pevisa.genericos.borrado;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Main {

    List<String> nombres = new ArrayList<>();

    static void main() {
        Eco<String> eco = new Eco<>();
        String s = eco.aplicar("Pevisa");
        IO.println("aplicar: " + s);

        List<String> a = new ArrayList<>();
        List<Integer> b = new ArrayList<>();
        IO.print("misma clase: " + (a.getClass() == b.getClass() )+
                " -> " + a.getClass().getName() + " == " + b.getClass().getName());

        IO.println("--- métodos de Mayusculas (reflexion)");
        for (Method m: Mayusculas.class.getDeclaredMethods()) {
            IO.println(m.getReturnType().getSimpleName() + " " +
                    "apply(" + m.getParameterTypes()[0].getSimpleName() + ") bridge=" + m.isBridge()
            + " synthetic=" + m.isSynthetic())
            ;
        }

        Function<String, String> f = new Mayusculas();
        IO.println("Mayusculas.apply: " + f.apply("Pevisa"));



    }
}
