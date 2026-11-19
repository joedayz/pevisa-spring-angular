package pe.com.pevisa.repuesto.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * TODO 3: Convertir esta clase en entidad JPA.
 *   - Agregar @Entity y @Table(name = "repuestos")
 *   - Marcar el campo id con @Id y @GeneratedValue(strategy = GenerationType.IDENTITY)
 *
 * TODO 4: Anotar cada campo con @Column:
 *   - codigo:      nullable=false, unique=true, length=50
 *   - descripcion: nullable=false, length=200
 *   - division:    nullable=false, length=50
 *   - precio:      nullable=false, precision=12, scale=2
 *   - stock:       nullable=false
 *   - createdAt:   nullable=false, updatable=false
 *   - updatedAt:   sin restricciones extra
 *
 * TODO 5: Implementar los callbacks del ciclo de vida JPA:
 *   - @PrePersist  → establecer createdAt y updatedAt con LocalDateTime.now()
 *   - @PreUpdate   → actualizar solo updatedAt
 *
 * Pregunta: ¿qué diferencia hay entre @PrePersist y @PreUpdate?
 *           ¿En qué momento exacto los llama Hibernate?
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
// TODO 3: agregar @Entity y @Table
public class Repuesto {

    // TODO 3: agregar @Id y @GeneratedValue
    private Long id;

    // TODO 4: agregar @Column con sus restricciones
    private String codigo;
    private String descripcion;
    private String division;
    private BigDecimal precio;
    private Integer stock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // TODO 5: agregar @PrePersist
    void antesDeInsertar() {
    }

    // TODO 5: agregar @PreUpdate
    void antesDeActualizar() {
    }
}
