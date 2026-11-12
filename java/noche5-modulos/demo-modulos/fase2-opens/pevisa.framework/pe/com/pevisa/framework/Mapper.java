package pe.com.pevisa.framework;

import java.lang.reflect.Field;

/**
 * Simula un framework de serializacion (al estilo Jackson/Gson): recibe un
 * Object cualquiera y usa reflexion para leer TODOS sus campos, incluso los
 * privados. Esto solo funciona si el modulo dueno de la clase hizo
 * "opens <paquete> to pevisa.framework" en su module-info.java.
 */
public class Mapper {

    public static String toJson(Object objeto) {
        StringBuilder json = new StringBuilder("{");
        Field[] campos = objeto.getClass().getDeclaredFields();

        for (int i = 0; i < campos.length; i++) {
            Field campo = campos[i];
            try {
                campo.setAccessible(true); // <- requiere "opens" del modulo origen
                Object valor = campo.get(objeto);
                json.append("\"").append(campo.getName()).append("\":\"").append(valor).append("\"");
                if (i < campos.length - 1) {
                    json.append(",");
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        return json.append("}").toString();
    }
}
