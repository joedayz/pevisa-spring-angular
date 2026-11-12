package pe.com.pevisa.anotaciones;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// Solo existe en el .class, pero, la JVM no la carga en tiempo de ejecución
@Retention(RetentionPolicy.CLASS)
public @interface EnClase {
}
