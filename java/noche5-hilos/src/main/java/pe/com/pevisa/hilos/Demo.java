package pe.com.pevisa.hilos;

public class Demo {

    static void main() {

        System.out.println("start main " + Thread.currentThread().getName());

        MiHilo hilo = new MiHilo();
        hilo.start();

        Runnable tarea  = () ->{
            System.out.println("Ejecutando tarea en " + Thread.currentThread().getName());
        };

        Thread hilo2 = new Thread(tarea);
        hilo2.start();
        System.out.println("end main");
    }
}

class MiHilo extends Thread {
    @Override
    public void run() {
        System.out.println("Mi hilo en ejecución " + Thread.currentThread().getName());
    }
}