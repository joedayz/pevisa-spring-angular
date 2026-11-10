package pe.com.pevisa.hilos;

import java.util.concurrent.atomic.AtomicInteger;

public class Hilos {

    static void main() throws InterruptedException {
        AtomicInteger stock = new AtomicInteger(8);

        Thread salida = Thread.ofVirtual().name("salida-callao").start(() -> {
           stock.addAndGet(-2);
        });

        salida.join();

        System.out.println("Stock final: " + stock.get());
        System.out.println("El hilo es virtual? " + salida.isVirtual());
    }
}
