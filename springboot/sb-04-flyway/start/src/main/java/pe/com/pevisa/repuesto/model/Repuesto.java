package pe.com.pevisa.repuesto.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * TODO 1: Agregar el campo "marca" (String, nullable=false, length=100).
 *
 *         Pregunta clave: ¿cuál es el orden correcto de los pasos?
 *           A) Agregar el campo Java → hacer deploy → crear la migración SQL
 *           B) Crear la migración SQL → hacer deploy → agregar el campo Java
 *           C) Crear la migración SQL y el campo Java en el mismo commit → deploy
 *
 *         ¿Qué pasa si haces A con ddl-auto: validate?
 */
@Entity
@Table(name = "repuestos")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Repuesto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo", nullable = false, unique = true, length = 50)
    private String codigo;

    @Column(name = "descripcion", nullable = false, length = 200)
    private String descripcion;

    @Column(name = "division", nullable = false, length = 50)
    private String division;

    // TODO 1: agregar campo marca aquí

    @Column(name = "precio", nullable = false, precision = 12, scale = 2)
    private BigDecimal precio;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    void antesDeInsertar() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    void antesDeActualizar() {
        updatedAt = LocalDateTime.now();
    }
}
