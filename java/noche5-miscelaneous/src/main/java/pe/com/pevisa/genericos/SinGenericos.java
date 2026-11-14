package pe.com.pevisa.genericos;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"rawtypes", "unchecked"})
public class SinGenericos {

    static void main() {
        List nombres = new ArrayList();
        nombres.add("Pevisa");
        nombres.add(42);

        for(Object o : nombres) {
            String s = (String) o; // cast
            System.out.println(s.toUpperCase());
        }
    }
}
