package pe.com.pevisa.hilos;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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


class AlmacenConLock{

    private int stock;
    private final Lock candado = new ReentrantLock();

    public AlmacenConLock(int stockInicial) {
        this.stock = stockInicial;
    }

    void salida(int cantidad){
        candado.lock();
        try {
            stock -= cantidad;
        } finally {
            candado.unlock();
        }
    }

    int stock(){
        candado.lock();
        try {
            return stock;
        } finally {
            candado.unlock();
        }
    }
}




public class MainHilos{

    static void main() throws InterruptedException, ExecutionException {
        Almacen callao = new Almacen(100);
        List<Thread> hilosSalida = new ArrayList<>();
        for(int i = 0; i < 50; i++){
            hilosSalida.add(Thread.ofVirtual().start(() -> callao.salida(1)));
        }
        for(Thread hilo: hilosSalida){
            hilo.join();
        }
        System.out.println("Almacen con Lock, 50 salidas de 1: " + callao.stock() +
                " (esperado 50)");



        AtomicInteger stock = new AtomicInteger(100);
        Thread salida = Thread.ofVirtual().name("salida-callao").start(() -> {
            stock.addAndGet(-2);
        });
        salida.join();
        System.out.println("Stock de ELE-017 en Callao: " + stock.get() + " (virtual: " + salida.isVirtual() + ")");

        //Locks
        AlmacenConLock ate = new AlmacenConLock(100);
        List<Thread> hilosSalida2 = new ArrayList<>();
        for(int i = 0; i < 50; i++){
            hilosSalida2.add(Thread.ofVirtual().start(() -> {
                ate.salida(1);
            }));
        }
        for(Thread hilo: hilosSalida2){
            hilo.join();
        }

        System.out.println("Almacen con Lock, 50 salidas de 1: " + ate.stock() +
                " (esperado 50)");

        //ExecutorService
        try(ExecutorService hilosDeSalida = Executors.newVirtualThreadPerTaskExecutor()) {

            List<Future<?>> tareas = new ArrayList<>();
            for(int i = 0; i < 20; i++){
                tareas.add(hilosDeSalida.submit(() -> {
                    callao.salida(1);;
                }));
            }
            for(Future<?> tarea: tareas){
                tarea.get();
            }
        }

        System.out.println("Almacen tras 20 salidas via ExecutorService: " + callao.stock() +
                " (esperado 30)");

    }


}