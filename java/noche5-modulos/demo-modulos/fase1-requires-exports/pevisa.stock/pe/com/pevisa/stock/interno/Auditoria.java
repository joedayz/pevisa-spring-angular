package pe.com.pevisa.stock.interno;

// Clase de uso interno: solo pevisa.ventas puede verla (exports ... to pevisa.ventas).
public class Auditoria {

    public static void registrar(String evento) {
        System.out.println("[AUDITORIA interna de stock] " + evento);
    }
}
