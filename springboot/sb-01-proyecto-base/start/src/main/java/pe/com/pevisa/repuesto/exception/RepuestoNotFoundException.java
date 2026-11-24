package pe.com.pevisa.repuesto.exception;

public class RepuestoNotFoundException extends RuntimeException{

    public RepuestoNotFoundException(Long id) {
        super("Repuesto no encontrado con id: " + id);
    }

    public RepuestoNotFoundException(String codigo) {
        super("Repuesto no encontrado con código: " + codigo);
    }
}
