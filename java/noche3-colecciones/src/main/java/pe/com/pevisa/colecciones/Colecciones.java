package pe.com.pevisa.colecciones;


import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

record Sku(String codigo, String division, String almacen, int stock, int minimo){
    boolean bajo(){
        return stock < minimo;
    }
}


public class Colecciones {

    public static void main(String[] args) {

        List<Sku> catalogo = new ArrayList<>(List.of(
                new Sku("ELE-017", "BATERIAS", "CALLAO", 8, 12),
                new Sku("BOS-100", "BOSCH", "CALLAO", 40, 12),
                new Sku("DEN-220", "DENSO", "ATE", 6, 10),
                new Sku("ILU-015", "ILUMINACION", "ATE", 80, 20),
                new Sku("SUS-102", "SUSPENSION", "CALLAO", 16, 20)));

        System.out.println("Catalogo de productos  " + catalogo.size() + " productos");

        // Lambda

        Predicate<Sku> enCallao = sku -> sku.almacen().equals("CALLAO");

        System.out.println("Existe un almacen en CALLAO? " +
                catalogo.stream()
                        .anyMatch(enCallao));

        //filter y map
        List<String> bajos = catalogo.stream()
                .filter(sku -> sku.bajo())
                .map(sku -> sku.codigo())
                .toList();
        System.out.println("Productos con stock bajo: " + bajos);

        //Ordenar con Comparator
        catalogo.sort(Comparator.comparingInt(sku -> sku.stock()));
        System.out.println("Catalogo ordenado por stock: " + catalogo);

        //Sumar
        int totalCallao = catalogo.stream()
                .filter(enCallao)
                .mapToInt(Sku::stock) //metodo de referencia a stock es igual a sku -> sku.stock()
                .sum();

        System.out.println("Total stock en CALLAO: " + totalCallao);

        //Agrupar
        TreeMap<String, Integer> porAlmacen = catalogo.stream()
                .collect(Collectors.groupingBy(Sku::almacen, TreeMap::new, Collectors.summingInt(Sku::stock)));
        System.out.println("Total stock por almacen: " + porAlmacen);

        //Optional
        Optional<Sku> buscado = catalogo.stream()
                .filter(sku -> sku.codigo().equals("ILU-999"))
                .findFirst();
        System.out.println(buscado.map(Sku::codigo).orElse("No encontrado"));

        Map<String, Integer> minimos = new HashMap<>();
        minimos.put("ELE-017", 12);
        minimos.put("BOS-100", 12);
        System.out.println("Mínimo de ELE-017: " + minimos.get("ELE-017"));
        System.out.println("Mínimo de NEU-190: " + minimos.getOrDefault("NEU-190", 0));

    }
}
