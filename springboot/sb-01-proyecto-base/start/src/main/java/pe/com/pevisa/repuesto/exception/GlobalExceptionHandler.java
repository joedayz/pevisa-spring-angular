package pe.com.pevisa.repuesto.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     {
     "type": "https://pevisa.com.pe/errores/repuesto-no-encontrado",
     "title": "Repuesto no encontrado",
     "status": 404,
     "detail": "No existe el repuesto con id 99",
     "instance": "/api/repuestos/99"
     }
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex){
        String detalle = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, detalle
        );
        problem.setTitle("Error de validación");
        problem.setType(URI.create("https://pevisa.com.pe/errores/error-de-validacion"));

        return problem;
    }

}
