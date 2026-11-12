package pe.com.pevisa.anotaciones.diseno;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PoliticasNegocio {
    PoliticaNegocio[] value();
}
