package pe.com.pevisa.repuesto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.stream.Collectors;

/**
 * Manejador global de excepciones.
 *
 * @RestControllerAdvice intercepta excepciones lanzadas desde cualquier
 * @RestController y les da una respuesta HTTP estructurada.
 *
 * Usamos ProblemDetail (RFC 7807) — el estándar de la industria para
 * errores de API. Tiene campos fijos: type, title, status, detail, instance.
 *
 * NOTA: Esto es un adelanto mínimo. En SB-07 construimos el handler completo:
 *   - Errores de negocio con códigos propios
 *   - Errores de concurrencia optimista
 *   - Errores de constraint de BD
 *   - Logging estructurado de cada error
 *
 * AOP (Aspect-Oriented Programming) entrará en escena en SB-10 (Auditoría).
 * Este @ControllerAdvice ya usa el mismo mecanismo por debajo (proxies),
 * pero sin que lo veamos explícitamente todavía.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 404 cuando no se encuentra un repuesto.
     */
    @ExceptionHandler(RepuestoNotFoundException.class)
    public ProblemDetail handleNotFound(RepuestoNotFoundException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND, ex.getMessage()
        );
        problem.setTitle("Repuesto no encontrado");
        problem.setType(URI.create("https://pevisa.com.pe/errores/repuesto-no-encontrado"));
        return problem;
    }

    /**
     * 400 cuando el body del request falla las validaciones (@Valid).
     * Muestra todos los errores de una vez, no solo el primero.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex) {
        String detalle = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, detalle
        );
        problem.setTitle("Error de validación");
        problem.setType(URI.create("https://pevisa.com.pe/errores/validacion"));
        return problem;
    }
}
