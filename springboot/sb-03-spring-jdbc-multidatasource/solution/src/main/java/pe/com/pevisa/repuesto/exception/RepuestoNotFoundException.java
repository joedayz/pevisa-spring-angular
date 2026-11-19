package pe.com.pevisa.repuesto.exception;

// Sin cambios respecto a SB-01
public class RepuestoNotFoundException extends RuntimeException {

    public RepuestoNotFoundException(Long id) {
        super("No se encontró el repuesto con id: " + id);
    }

    public RepuestoNotFoundException(String codigo) {
        super("No se encontró el repuesto con código: " + codigo);
    }
}
