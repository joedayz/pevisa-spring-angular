package pe.com.pevisa.repuesto.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.JdbcTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.ActiveProfiles;
import pe.com.pevisa.repuesto.repository.jdbc.PedidoLegadoRepository;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@ActiveProfiles("test")
public class PedidoLegadoRepositoryTest {

    @Autowired
    JdbcClient jdbcClient;

    PedidoLegadoRepository repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new PedidoLegadoRepository(jdbcClient);

        jdbcClient.sql("""

                CREATE TABLE IF NOT EXISTS pedidos (
                    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
                    numero_pedido   VARCHAR(20)    NOT NULL,
                    codigo_repuesto VARCHAR(50)    NOT NULL,
                    distribuidor    VARCHAR(100)   NOT NULL,
                    cantidad        INTEGER        NOT NULL,
                    precio_unitario NUMERIC(12,2)  NOT NULL,
                    total           NUMERIC(14,2),
                    estado          VARCHAR(20)    NOT NULL DEFAULT 'PENDIENTE',
                    fecha_pedido    TIMESTAMP      DEFAULT NOW()
                )

                """).update();

        jdbcClient.sql("DELETE FROM PEDIDOS").update();

        jdbcClient.sql("""
                INSERT INTO pedidos
                    (numero_pedido, codigo_repuesto, distribuidor, cantidad, precio_unitario, total, estado)
                VALUES
                    ('PED-001', 'EMP-TOYOTA-001', 'Distribuidora Lima', 10, 185.00, 1850.00, 'ENTREGADO'),
                    ('PED-002', 'FIL-BOSCH-002',  'Autopartes Sur',      5,  28.50,  142.50, 'PENDIENTE'),
                    ('PED-003', 'EMP-TOYOTA-001', 'Repuestos Arequipa', 20, 185.00, 3700.00, 'ENTREGADO')
                """).update();

    }



    @Test
    @DisplayName("findAll retorna todos los pedidos")
    void findAll(){
        assertThat(repositorio.findAll()).hasSize(3);
    }

    @Test
    @DisplayName("findByNumeroPedido retorna el pedido correcto")
    void findByNumeroPedido() {
        var resultado = repositorio.findByNumeroPedido("PED-001");
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getCodigoRepuesto()).isEqualTo("EMP-TOYOTA-001");
        assertThat(resultado.get().getEstado()).isEqualTo("ENTREGADO");

    }

    @Test
    @DisplayName("findByCodigoRepuesto retorna pedidos del repuesto")
    void findByCodigoRepuesto() {
        var pedidos = repositorio.findByCodigoRepuesto("EMP-TOYOTA-001");
        assertThat(pedidos).hasSize(2);
        assertThat(pedidos).allMatch(p -> p.getCodigoRepuesto().equals("EMP-TOYOTA-001"));
    }

    @Test
    @DisplayName("findByEstado filtra correctamente")
    void findByEstado() {
        var pendientes = repositorio.findByEstado("PENDIENTE");
        assertThat(pendientes).hasSize(1);
        assertThat(pendientes.get(0).getNumeroPedido()).isEqualTo("PED-002");
    }


    @Test
    @DisplayName("contarPorEstado devuelve la cuenta correcta")
    void contarPorEstado() {
        assertThat(repositorio.contarPorEstado("ENTREGADO")).isEqualTo(2L);
        assertThat(repositorio.contarPorEstado("PENDIENTE")).isEqualTo(1L);
    }
}
