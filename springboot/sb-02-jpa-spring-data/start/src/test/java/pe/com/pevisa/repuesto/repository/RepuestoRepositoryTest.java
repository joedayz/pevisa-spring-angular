package pe.com.pevisa.repuesto.repository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import pe.com.pevisa.repuesto.model.Repuesto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class RepuestoRepositoryTest {

    @Autowired
    private RepuestoRepository repositorio;

    @BeforeEach
    void setUp(){

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
    @DisplayName("findByCodigo retorna el respuesto correcto")
    void findByCodigo(){
        Optional<Repuesto> resultado = repositorio.findByCodigo("EMP-TOYOTA-001");
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getDescripcion()).isEqualTo("Empaquetadura motor Toyota Corolla 1.8");
    }

    @Test
    @DisplayName("findByDivisionIgnoreCase funciona con mayúsculas y minúsculas")
    void findByDivisionIgnoreCase() {
        List<Repuesto> bosch = repositorio.findByDivisionIgnoreCase("bosch");
        assertThat(bosch).hasSize(1);
        assertThat(bosch.get(0).getCodigo()).isEqualTo("FIL-BOSCH-002");
    }

}
