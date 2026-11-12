package pe.com.pevisa.anotaciones.diseno;

import java.util.Arrays;

public class Diseno {

    static void main() {
        var r = Tienda.class.getAnnotation(Revision.class);
        IO.println("Revision: v" + r.version() + ", " + r.fecha() + ", " + r.prioridad()
                + ", autor=" + r.autor().value() + ", etiquetas=" + Arrays.toString(r.etiquetas())
                + ", probadoCon=" + r.probadoCon().getSimpleName());
    }
}
