package pe.com.pevisa.repuesto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



/**
 * Punto de entrada de la aplicación PEVISA Repuestos.
 *
 * @SpringBootApplication equivale a:
 *   @Configuration          → esta clase puede declarar beans
 *   @EnableAutoConfiguration → Spring Boot configura lo que detecta en el classpath
 *   @ComponentScan          → escanea todos los @Component/@Service/@Repository/@Controller/@RestController
 *                             bajo este paquete y sus subpaquetes
 */


@SpringBootApplication
public class PevisaApplication {

    public static void main(String[] args) {
       SpringApplication.run(PevisaApplication.class, args);


    }
}
