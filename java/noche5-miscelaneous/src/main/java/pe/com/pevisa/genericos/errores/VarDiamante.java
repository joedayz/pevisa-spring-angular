package pe.com.pevisa.genericos.errores;

import java.util.ArrayList;

public class VarDiamante {

    static void main() {
        var lista = new ArrayList<>(); // var + diamante = ArrayList<Object>
        lista.add("Pevisa");

        String s = (String)lista.get(0); // error: incompatible types: Object cannot be converted to String
    }
}
