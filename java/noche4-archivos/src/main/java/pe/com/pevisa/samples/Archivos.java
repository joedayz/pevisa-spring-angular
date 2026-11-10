package pe.com.pevisa.samples;


import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

class StockInsuficienteException extends RuntimeException{
    StockInsuficienteException(String codigo, int pedido, int disponible ){
        super("Stock insuficiente para el producto " + codigo + ". Pedido: " + pedido + ", Disponible: " + disponible);
    }
}

record Producto(String codigo, String nombre, BigDecimal precio, int stock) {

    BigDecimal valor(){
        return precio.multiply(BigDecimal.valueOf(stock));
    }

    Producto salida(int cantidad) {
        if (cantidad > stock) {
            throw new StockInsuficienteException(codigo, cantidad, stock);
        }
        return new Producto(codigo, nombre, precio, stock - cantidad);
    }
}

public class Archivos {

    static void main() {
        LocalDate hoy = LocalDate.of(2026, 11, 9);
        System.out.println("Reposicion: " + hoy.plusDays(7));

        Path csv = Path.of("productos.csv");

        String contenido = """
                codigo;nombre;precio;stock
                ELE-017;Bateria 12V 75Ah;450.00;8
                BOS-100;Pastillas de Freno;89.90;40
                """;

        List<Producto> productos = new ArrayList<>();

        try{
            Files.writeString(csv, contenido);
            for(String linea: Files.readAllLines(csv)){
                if(linea.startsWith("codigo")){
                    continue;
                }
                String[] partes = linea.split(";");
                productos.add(new Producto(partes[0], partes[1],
                        new BigDecimal(partes[2]),
                        Integer.parseInt(partes[3])));
            }
        } catch (IOException e) {
            System.out.println("Error al escribir el archivo CSV: " + e.getMessage());
        }

        System.out.println("Productos cargados: " + productos.size());
        BigDecimal total = productos.stream()
                .map(Producto::valor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("Valor total de los productos: S/ " + total);


        Producto bateria = productos.getFirst();
        try{
            bateria.salida(20);
        }catch (StockInsuficienteException ex){
            System.out.println(ex.getMessage());
        }finally{
            System.out.println("Fin de la salida");
        }


        Producto despues = bateria.salida(2); // tenias 8
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime ahora = LocalDateTime.of(2026, 11, 9, 19,30);
        try(FileWriter kardex = new FileWriter("kardex.txt", true)){
            kardex.write(ahora.format(formato) + " salida " + despues.codigo() + " quedan " + despues.stock() + "\n");
            System.out.println("Kardex escrito");
        } catch (IOException ex) {
            System.out.println("Error al escribir el archivo de kardex: " + ex.getMessage());
        }

    }
}
