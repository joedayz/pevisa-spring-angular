package pe.com.pevisa.repuesto.exception;

/**
 * Excepción de negocio: el repuesto solicitado no existe.
 *
 * Extendemos RuntimeException (unchecked) para no obligar a quien
 * llama a capturarla. El @ControllerAdvice global la interceptará
 * y devolverá un 404 con cuerpo estructurado.
 *
 * En SB-07 profundizamos el manejo de errores con RFC 7807 (ProblemDetail)
 * y un @ControllerAdvice completo para todos los casos.
 */
public class RepuestoNotFoundException extends RuntimeException {

    public RepuestoNotFoundException(Long id) {
        super("No se encontró el repuesto con id: " + id);
    }

    public RepuestoNotFoundException(String codigo) {
        super("No se encontró el repuesto con código: " + codigo);
    }
}
