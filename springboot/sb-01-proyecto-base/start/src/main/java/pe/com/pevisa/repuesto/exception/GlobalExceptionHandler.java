package pe.com.pevisa.repuesto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * TODO 9: Anotar esta clase con @RestControllerAdvice
 *
 * TODO 10: Implementar el handler para RepuestoNotFoundException
 *   - Responder 404
 *   - Usar ProblemDetail.forStatusAndDetail(...)
 *   - Setear title y type
 *
 * TODO 11: Implementar el handler para MethodArgumentNotValidException
 *   - Responder 400
 *   - Recopilar TODOS los errores de validación, no solo el primero
 */
public class GlobalExceptionHandler {

    // TODO 10
    public ProblemDetail handleNotFound(RepuestoNotFoundException ex) {
        return null;
    }

    // TODO 11
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex) {
        return null;
    }
}
