package pe.com.pevisa.genericos.borrado;

import java.util.function.UnaryOperator;

public class Mayusculas implements UnaryOperator<String> {

    @Override
    public String apply(String s) {
        return s.toUpperCase();
    }
}
