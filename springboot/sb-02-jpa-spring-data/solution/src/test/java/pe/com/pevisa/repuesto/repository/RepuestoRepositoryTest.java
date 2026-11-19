package pe.com.pevisa.repuesto.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import pe.com.pevisa.repuesto.model.Repuesto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test de slice del Repository.
 *
 * @DataJpaTest levanta solo la capa JPA:
 *   - Configura H2 en memoria automáticamente (perfil test)
 *   - Crea el esquema con ddl-auto=create-drop
 *   - Cada test corre en su propia transacción que hace rollback al final
 *     → la BD queda limpia entre tests sin @BeforeEach que borre datos
 *
 * @ActiveProfiles("test") activa application-test.yml con H2.
 *
 * IMPORTANTE: @DataJpaTest no levanta controllers ni services.
 * Testea solo que las queries SQL son correctas.
 */
@DataJpaTest
@ActiveProfiles("test")
class RepuestoRepositoryTest {

    @Autowired
    private RepuestoRepository repositorio;

    @BeforeEach
    void setUp() {
        repositorio.save(Repuesto.builder()
                .codigo("EMP-TOYOTA-001")
                .descripcion("Empaquetadura motor Toyota Corolla 1.8")
                .division("EMPAQUETADURAS")
                .precio(new BigDecimal("185.00"))
                .stock(42)
                .build());

        repositorio.save(Repuesto.builder()
                .codigo("FIL-BOSCH-002")
                .descripcion("Filtro de aceite Bosch universal")
                .division("BOSCH")
                .precio(new BigDecimal("28.50"))
                .stock(5)   // stock bajo
                .build());

        repositorio.save(Repuesto.builder()
                .codigo("BAT-VOLTA-003")
                .descripcion("Batería Volta 60Ah 12V")
                .division("BATERIAS")
                .precio(new BigDecimal("320.00"))
                .stock(0)   // agotado
                .build());
    }

    @Test
    @DisplayName("findByCodigo retorna el repuesto correcto")
    void findByCodigo() {
        Optional<Repuesto> resultado = repositorio.findByCodigo("EMP-TOYOTA-001");
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getDivision()).isEqualTo("EMPAQUETADURAS");
    }

    @Test
    @DisplayName("findByDivisionIgnoreCase funciona con mayúsculas y minúsculas")
    void findByDivisionIgnoreCase() {
        List<Repuesto> bosch = repositorio.findByDivisionIgnoreCase("bosch");
        assertThat(bosch).hasSize(1);
        assertThat(bosch.get(0).getCodigo()).isEqualTo("FIL-BOSCH-002");
    }

    @Test
    @DisplayName("existsByCodigo retorna true si existe")
    void existsByCodigo() {
        assertThat(repositorio.existsByCodigo("BAT-VOLTA-003")).isTrue();
        assertThat(repositorio.existsByCodigo("XXX-NOEXISTE")).isFalse();
    }

    @Test
    @DisplayName("findRepuestosConStockBajo retorna solo los que tienen stock menor al umbral")
    void findRepuestosConStockBajo() {
        List<Repuesto> bajos = repositorio.findRepuestosConStockBajo(10);
        assertThat(bajos).hasSize(2); // stock 5 y stock 0
        // Deben estar ordenados por stock ascendente
        assertThat(bajos.get(0).getStock()).isLessThanOrEqualTo(bajos.get(1).getStock());
    }

    @Test
    @DisplayName("buscarEnDescripcion hace búsqueda case-insensitive")
    void buscarEnDescripcion() {
        List<Repuesto> resultado = repositorio.buscarEnDescripcion("toyota");
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getCodigo()).isEqualTo("EMP-TOYOTA-001");
    }

    @Test
    @DisplayName("@PrePersist establece createdAt y updatedAt automáticamente")
    void prePersist_estableceFechas() {
        Repuesto nuevo = repositorio.save(Repuesto.builder()
                .codigo("BUJ-NGK-010")
                .descripcion("Bujía NGK estándar")
                .division("DENSO")
                .precio(new BigDecimal("12.00"))
                .stock(200)
                .build());

        assertThat(nuevo.getCreatedAt()).isNotNull();
        assertThat(nuevo.getUpdatedAt()).isNotNull();
    }

    @Test
    @DisplayName("calcularValorInventarioPorDivision suma precio * stock")
    void calcularValorInventario() {
        // EMPAQUETADURAS: 185.00 * 42 = 7770.00
        BigDecimal valor = repositorio.calcularValorInventarioPorDivision("EMPAQUETADURAS");
        assertThat(valor).isEqualByComparingTo(new BigDecimal("7770.00"));
    }
}
