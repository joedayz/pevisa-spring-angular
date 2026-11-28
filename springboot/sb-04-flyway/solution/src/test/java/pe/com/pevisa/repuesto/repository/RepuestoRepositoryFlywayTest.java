package pe.com.pevisa.repuesto.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import pe.com.pevisa.repuesto.repository.jpa.RepuestoJpaRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test de integración JPA + Flyway.
 *
 * @DataJpaTest con @ActiveProfiles("test") activa application-test.yml
 * que configura Flyway con las migraciones de db/migration-test/.
 *
 * Lo que prueba este test:
 *   1. Que las migraciones V1, V2, V3 corren sin errores en H2
 *   2. Que los datos semilla de V2 están presentes
 *   3. Que la columna "marca" (agregada en V3) existe y tiene datos
 *   4. Que las queries JPA funcionan contra el schema Flyway
 *
 * Esto es más valioso que un test unitario solo: valida que
 * el schema real (creado por migraciones) es compatible con las entidades JPA.
 */
@DataJpaTest
@ActiveProfiles("test")
class RepuestoRepositoryFlywayTest {

    @Autowired
    RepuestoJpaRepository repositorio;

    @Test
    @DisplayName("Las migraciones V1+V2 crean datos semilla — hay repuestos en BD")
    void migracionesCreanDatosSemilla() {
        List<?> repuestos = repositorio.findAll();
        assertThat(repuestos).isNotEmpty();
        assertThat(repuestos.size()).isGreaterThanOrEqualTo(5);
    }

    @Test
    @DisplayName("V3 agregó columna marca — se puede leer sin error")
    void v3AgregaColumnaMarca() {
        var repuesto = repositorio.findByCodigo("EMP-TOYOTA-001");
        assertThat(repuesto).isPresent();
        assertThat(repuesto.get().getMarca()).isEqualTo("Toyota");
    }

    @Test
    @DisplayName("Repuesto AGOTADO existe en datos semilla")
    void repuestoAgotadoEnSemilla() {
        var agotados = repositorio.findRepuestosConStockBajo(1);
        assertThat(agotados).anyMatch(r -> r.getStock() == 0);
    }

    @Test
    @DisplayName("findByDivisionIgnoreCase funciona con schema Flyway")
    void findByDivisionConSchemaFlyway() {
        var bosch = repositorio.findByDivisionIgnoreCase("BOSCH");
        assertThat(bosch).isNotEmpty();
        assertThat(bosch).allMatch(r -> r.getDivision().equalsIgnoreCase("BOSCH"));
    }

    @Test
    @DisplayName("created_at se establece automáticamente al insertar")
    void createdAtAutomatico() {
        // Los datos de V2 tienen created_at porque el DEFAULT NOW() lo asigna
        var repuesto = repositorio.findByCodigo("EMP-TOYOTA-001").orElseThrow();
        assertThat(repuesto.getCreatedAt()).isNotNull();
    }
}
