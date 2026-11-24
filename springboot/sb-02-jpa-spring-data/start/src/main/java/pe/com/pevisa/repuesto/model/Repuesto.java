package pe.com.pevisa.repuesto.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


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
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    void antesDeActualizar() {
        updatedAt = LocalDateTime.now();
    }
}
