package pe.com.pevisa.hilos;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

class Almacen {

    private int stock;

    Almacen(int stockInicial){
        this.stock = stockInicial;
    }

    synchronized void salida(int cantidad){
        int disponible = stock;
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        this.stock = disponible - cantidad;
    }

    synchronized int stock(){
        return stock;
    }
}

public class MainHilos{

    static void main() throws InterruptedException {
        Almacen callao = new Almacen(100);
        List<Thread> hilosSalida = new ArrayList<>();
        for(int i = 0; i < 50; i++){
            hilosSalida.add(Thread.ofVirtual().start(() -> callao.salida(1)));
        }
        for(Thread hilo: hilosSalida){
            hilo.join();
        }
        System.out.println("Stock final: " + callao.stock());


        AtomicInteger stock = new AtomicInteger(100);
        Thread salida = Thread.ofVirtual().name("salida-callao").start(() -> {
            stock.addAndGet(-2);
        });
        salida.join();
        System.out.println("Stock final: " + stock.get());

        //Locks

        //ExecutorServices

    }


}