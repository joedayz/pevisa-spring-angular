package pe.com.pevisa.hilos;

public class VariosHilos {

    public static void main(String[] args) {
        for (int i = 0; i <=5; i++) {
            Thread hilo = new Thread(() -> {
                System.out.println("Ejecutando tarea en " + Thread.currentThread().getName());
            });
            hilo.start();
        }
    }
}
