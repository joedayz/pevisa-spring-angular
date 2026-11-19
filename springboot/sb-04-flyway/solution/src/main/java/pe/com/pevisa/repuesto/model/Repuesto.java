package pe.com.pevisa.repuesto.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * CAMBIO en SB-04:
 *   + columna "marca" agregada en V3__agregar_columna_marca.sql
 *
 * Con ddl-auto: validate, Hibernate compara esta entidad contra
 * el schema real de Postgres al arrancar la app.
 * Si falta la columna "marca" en la BD, arranca con:
 *   SchemaManagementException: Schema-validation: missing column [marca]
 *
 * El orden correcto es:
 *   1. Crear V3 con el ALTER TABLE
 *   2. Agregar el campo en la entidad Java
 *   3. Deploy — Flyway corre V3 primero, luego Hibernate valida y pasa.
 *
 * Nunca al revés: si agregas el campo Java antes de la migración,
 * la validación falla en el deploy anterior al tuyo.
 */
@Entity
@Table(name = "repuestos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    /** Nuevo en V3 — la marca del fabricante */
    @Column(name = "marca", nullable = false, length = 100)
    private String marca;

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
