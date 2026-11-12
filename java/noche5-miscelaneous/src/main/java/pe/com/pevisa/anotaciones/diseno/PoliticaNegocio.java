package pe.com.pevisa.anotaciones.diseno;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Repeatable(PoliticasNegocio.class)
public @interface PoliticaNegocio {
    String nombre() default "por defecto";
    String[] paises();
    String value();
}
