package pe.com.pevisa.repuesto.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Wrapper de paginación para las respuestas del API.
 *
 * Spring Data devuelve Page<T> que incluye metadata útil.
 * Este DTO la expone al cliente de forma limpia sin acoplar
 * la respuesta a las clases internas de Spring.
 *
 * Respuesta JSON:
 * {
 *   "contenido": [...],
 *   "paginaActual": 0,
 *   "totalPaginas": 5,
 *   "totalElementos": 48,
 *   "tamanioPagina": 10,
 *   "ultima": false
 * }
 */
@Data
@Builder
public class PageResponse<T> {

    private List<T> contenido;
    private int paginaActual;
    private int totalPaginas;
    private long totalElementos;
    private int tamanioPagina;
    private boolean ultima;
}
