package pe.com.pevisa.repuesto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.stream.Collectors;

/**
 * Sin cambios respecto a SB-01.
 * En SB-07 crece con manejo de DataIntegrityViolationException
 * (cuando Postgres rechaza un código duplicado por constraint UNIQUE).
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PedidoNotFoundException.class)
    public ProblemDetail handlePedidoNotFound(PedidoNotFoundException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setTitle("Pedido no encontrado");
        problem.setType(URI.create("https://pevisa.com.pe/errores/pedido-no-encontrado"));
        return problem;
    }

    @ExceptionHandler(RepuestoNotFoundException.class)
    public ProblemDetail handleNotFound(RepuestoNotFoundException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setTitle("Repuesto no encontrado");
        problem.setType(URI.create("https://pevisa.com.pe/errores/repuesto-no-encontrado"));
        return problem;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex) {
        String detalle = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, detalle);
        problem.setTitle("Error de validación");
        problem.setType(URI.create("https://pevisa.com.pe/errores/validacion"));
        return problem;
    }
}
