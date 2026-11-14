package pe.com.pevisa.genericos.borrado;

public class Numerico<T extends Number> {

    public double doble(T t){
        return t.doubleValue() * 2;
    }
}
