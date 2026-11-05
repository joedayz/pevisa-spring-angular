package pe.com.pevisa.clases;

import java.util.List;

public class Clases {

    static void main() {
        Bateria bateria = new Bateria("ELE-017", 8, 12, 12);
        Pieza pastillas = new Pieza("B0S-100", Division.BOSCH, 40, 12);

        List<Pieza> piezas = List.of(bateria, pastillas);

        for(Pieza pieza: piezas){
            System.out.println(pieza.detalle() + (pieza.bajo() ? " . BAJO STOCK" : " . OK STOCK"));
        }


        Almacen callao = new Almacen("CALLAO", "Av. Argentina 123");
        System.out.println(callao.codigo() + " . " + callao.direccion());

    }

}


interface ConStock{
    int stock();
    int minimo();

    default boolean bajo(){
        return stock() < minimo();
    }

}

interface Otro{
    void foo();
}

enum Division{
    BOSCH, BATERIAS
}


//Clase Padre
class Pieza extends Object implements ConStock, Otro{
    private String codigo;
    private Division division;
    private int minimo;
    private int stock;

    public Pieza(String codigo, Division division, int stock, int minimo){
        this.codigo = codigo;
        this.division = division;
        this.stock = stock;
        this.minimo = minimo;
    }

    public String detalle(){
        return codigo + " . " + division;
    }


    public String codigo(){
        return codigo;
    }
    @Override
    public int stock(){
        return stock;
    }

    @Override
    public int minimo() {
        return minimo;
    }

    @Override
    public void foo() {

    }
}

//Clase Hija
class Bateria extends Pieza{
    private int volts;

    public Bateria(String codigo, int stock, int minimo, int volts){  // new Bateria()
        super(codigo, Division.BATERIAS, stock, minimo);
        this.volts = volts;
    }

}


record Almacen (String codigo, String direccion){

    // codigo() y direccion() son metodos que ya vienen por defecto en un record
    // toString() tambien viene por defecto en un record
    // hashCode() y equals() tambien vienen por defecto en un record
    // constructor tambien viene por defecto en un record
    // Lombok ---> despues de java 17
}


