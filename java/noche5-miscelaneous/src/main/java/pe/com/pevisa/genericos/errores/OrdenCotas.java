package pe.com.pevisa.genericos.errores;

import java.math.BigDecimal;

public class OrdenCotas {



    // error: la clase Number debe ir primero; después de & solo van interfaces
    //static <T extends Comparable<T> & Number> T max(T a, T b){
    static <T extends Number & Comparable<T>>  T max(T a, T b){
       return a.compareTo(b) > 0 ? a : b;
    }

    public static void main(String[] args) {
        System.out.println(max(3, 5));
        System.out.println(max(3.14, 2.71));
        System.out.println(max(new BigDecimal("10.00"), new BigDecimal("9.99")));
    }
}
