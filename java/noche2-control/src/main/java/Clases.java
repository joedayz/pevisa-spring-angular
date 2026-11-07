import java.util.List;

interface ConStock {

    int stock();

    int minimo();

    default boolean bajo(){
        return stock() < minimo();
    }
}

enum Division{
    GASKETS("gaskets"),
    DENSO("denso"),
    BOSH("bosh"),
    ILUMINACION("iluminacion"),
    ELECTRICOS("electricos"),
    RODAMIENTOS("rodamientos"),
    SUSPENSION("suspension"),
    LUBRICANTES("lubricantes"),
    BATERIAS("baterias"),
    NEUMATICOS("neumaticos");

    private final String nombre;

    Division(String nombre) {
        this.nombre = nombre;
    }

    String nombre(){
        return nombre;
    }
}

class Pieza implements ConStock{

    private static int creadas = 0; //contador statico de piezas creadas

    private final String codigo;
    private final Division division;
    private final int minimo;
    private int stock;

    public Pieza(String codigo, Division division, int stock, int minimo) {
        if (stock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
        this.codigo = codigo;
        this.division = division;
        this.minimo = minimo;
        this.stock = stock;
        creadas++;
    }

    @Override
    public int stock() {
        return stock;
    }

    @Override
    public int minimo() {
        return minimo;
    }

    static int creadas() {
        return creadas;
    }

    void ingresar(int cantidad){
        stock += cantidad;
    }

    String detalle(){
        return codigo + " . " + division.nombre();
    }

    @Override
    public String toString() {
        return detalle() + " . " + stock + " u.";
    }
}

class Bateria extends Pieza{
    private final int volts;

    public Bateria(String codigo, int stock, int minimo, int volts) {
        super(codigo, Division.BATERIAS, stock, minimo);
        this.volts = volts;
    }

    @Override
    String detalle() {
        return super.detalle() + " . "  + volts + " V";
    }
}

record Almacen (String codigo, String direccion){

}

public class Clases{
    static void main() {
        Bateria bateria = new Bateria("ELE-017", 8, 12, 12);
        Pieza pastillas = new Pieza ("B0S-100", Division.BOSH, 40, 12);


        List<Pieza> piezas = List.of(bateria, pastillas);

        for(Pieza pieza: piezas){
            System.out.println(pieza.detalle() + (pieza.bajo() ? ". BAJO": ". OK"));
        }

        bateria.ingresar(4);
        System.out.println(bateria);
        System.out.println("Piezas creadas: " + Pieza.creadas());


        Almacen callao = new Almacen("CALLAO", "Av. Elmer Faucett 717");
        Almacen mismo = new Almacen("CALLAO", "Av. Elmer Faucett 717");

        System.out.println("Almacenes iguales: " + callao.equals(mismo));

        try {
            new Pieza("DEN-220", Division.DENSO, -10, 10);
        }catch (IllegalArgumentException ex) {
            System.out.println("Error: " + ex.getMessage());
        }


    }
}