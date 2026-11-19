package pe.com.pevisa.repuesto.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad JPA — el mismo modelo de SB-01, ahora mapeado a la tabla "repuestos".
 *
 * CAMBIOS respecto a SB-01:
 *   + @Entity      → Hibernate sabe que esta clase es una tabla
 *   + @Table       → nombre explícito de la tabla (evitar nombres reservados)
 *   + @Id          → clave primaria
 *   + @GeneratedValue → Postgres genera el id con IDENTITY (serial/bigserial)
 *   + @Column      → control fino: nullable, length, unique, nombre de columna
 *   + createdAt / updatedAt → campos de auditoría básica
 *                             (en SB-10 los manejará @EntityListeners automáticamente)
 *
 * LO QUE NO CAMBIÓ:
 *   - RepuestoService no sabe que ahora hay JPA — habla con la misma interfaz.
 *   - RepuestoController no cambió ni una línea.
 *   - Los DTOs no cambiaron.
 *
 * Eso es exactamente el beneficio de haber definido la interfaz en SB-01.
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

    /**
     * Código interno PEVISA, ej: "EMP-TOYOTA-001".
     * unique = true → constraint UNIQUE en BD, no solo validación en Java.
     * nullable = false → NOT NULL en la columna.
     */
    @Column(name = "codigo", nullable = false, unique = true, length = 50)
    private String codigo;

    @Column(name = "descripcion", nullable = false, length = 200)
    private String descripcion;

    /**
     * División comercial: BOSCH, DENSO, BATERIAS, EMPAQUETADURAS.
     *
     * Guardamos como String (@Enumerated(STRING) si fuera enum).
     * En SB-04 (Flyway) agregaremos un CHECK constraint en BD.
     */
    @Column(name = "division", nullable = false, length = 50)
    private String division;

    /**
     * precision = 12, scale = 2 → hasta 9.999.999.999,99
     * Nunca uses double/float para dinero.
     */
    @Column(name = "precio", nullable = false, precision = 12, scale = 2)
    private BigDecimal precio;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    /**
     * Auditoría básica — se establece manualmente por ahora.
     * En SB-10 esto lo hará @CreatedDate / @LastModifiedDate automáticamente.
     * updatable = false → una vez insertado, Hibernate no lo sobrescribe.
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * @PrePersist se ejecuta justo antes de INSERT.
     * @PreUpdate  se ejecuta justo antes de UPDATE.
     * Son callbacks del ciclo de vida JPA — no necesitan AOP todavía.
     */
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
