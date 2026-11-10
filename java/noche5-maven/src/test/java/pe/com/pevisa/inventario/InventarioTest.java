package pe.com.pevisa.inventario;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InventarioTest {

    private Inventario callao;

    @BeforeEach
    void preparar(){
        callao = new Inventario();
        callao.ingresar("ELE-017", 8);
    }

    @Test
    void ingresoSumaAlStock(){
        callao.ingresar("ELE-017", 4);
        assertEquals(12,  callao.stockDe("ELE-017"));
    }

    @Test
    void salidaRestaDelStock(){
        callao.salida("ELE-017", 2);
        assertEquals(6,  callao.stockDe("ELE-017"));
    }

    @Test
    void salidaMayorAlStockFalla(){
        StockInsuficienteException ex =
                assertThrows(StockInsuficienteException.class, () -> callao.salida("ELE-017", 10));
        assertEquals("Stock insuficiente para el producto ELE-017: se intentó retirar 10 pero solo hay 8 disponibles.",
                ex.getMessage());

    }

    @Test
    void ingresarConCeroFalla(){
        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> callao.ingresar("ELE-017", 0));
        assertEquals("La cantidad a ingresar debe ser mayor a cero", ex.getMessage());
    }

    @Test
    void productoSinMovimientosTieneCero(){
        assertEquals(0, callao.stockDe("NEU-190"));
    }

}
