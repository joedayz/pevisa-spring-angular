package pe.com.pevisa.hilos;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class HilosVirtualesMasivo {

    static void main() {
        int totalHilos = 1_000_000;
        AtomicInteger completados = new AtomicInteger(0);

        long inicio = System.currentTimeMillis();

        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < totalHilos; i++) {
                executor.submit(() -> {
                    try {
                        Thread.sleep(100);
                        completados.incrementAndGet();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                });
            }

        }


        long fin = System.currentTimeMillis();


        System.out.println("Total hilos completados: " + completados.get() + " de " + totalHilos);

        System.out.println("Tiempo de ejecución: " + (fin - inicio) + " ms");
    }
}
