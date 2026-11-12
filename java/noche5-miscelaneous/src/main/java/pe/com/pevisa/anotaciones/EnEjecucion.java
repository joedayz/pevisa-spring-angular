package pe.com.pevisa.anotaciones;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// Se guarda en el .class y la JVM la carga en tiempo de ejecución
@Retention(RetentionPolicy.RUNTIME)
public @interface EnEjecucion {
}
