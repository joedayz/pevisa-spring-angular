package pe.com.pevisa.anotaciones;

import java.util.Arrays;

public class Retencion {

    void main(String[] args) throws NoSuchFieldException, NoSuchMethodException {
        IO.println("Anotaciones de Cuenta visibles en tiempo de ejecución");

        //Auditable no es anotacion de clase y por eso no se imprime
        Arrays.stream(Cuenta.class.getAnnotations()).forEach(a -> IO.println(" " + a));

        IO.println("EnFuente? " + Cuenta.class.isAnnotationPresent(EnFuente.class));
        IO.println("EnClase? " + Cuenta.class.isAnnotationPresent(EnClase.class));
        IO.println("EnEjecucion? " + Cuenta.class.isAnnotationPresent(EnEjecucion.class));

        var campo = Cuenta.class.getDeclaredField("saldo");
        IO.println("campo saldo: " + Arrays.toString(campo.getAnnotations()));
        var metodo = Cuenta.class.getMethod("depositar", double.class);
        IO.println("metodo depositar: " + Arrays.toString(metodo.getAnnotations()));

    }
}
