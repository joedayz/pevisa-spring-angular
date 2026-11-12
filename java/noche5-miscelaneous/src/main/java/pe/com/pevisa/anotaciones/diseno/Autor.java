package pe.com.pevisa.anotaciones.diseno;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Autor {
    String value();
}
