package pe.com.pevisa.anotaciones.diseno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Revision {
    int version() default 1; // primitivo no obligatorio
    String fecha(); // String obligatorio por que no tiene default
    Class<?> probadoCon() default Object.class;
    Prioridad prioridad() default Prioridad.MEDIA;
    Autor autor() default @Autor("anónimo");       // otra anotación
    String[] etiquetas() default {};               // arreglo
    long TIEMPO_MAX = 60 * 1000;
}
