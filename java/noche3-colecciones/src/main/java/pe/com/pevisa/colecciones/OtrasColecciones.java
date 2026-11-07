package pe.com.pevisa.colecciones;

import java.util.*;

public class OtrasColecciones {

    public static void main(String[] args) {

        Iterable<String> nombres = List.of(
                "Ana", "Pedro", "Luis"
        );

        nombres.forEach(nombre -> System.out.println("Nombre: " + nombre));

        System.out.println("----------------------------");

        Collection<String> nombresCollection = new ArrayList<>();

        nombresCollection.add("Maria");
        nombresCollection.add("Juan");
        nombresCollection.add("Luis");

        nombresCollection.forEach(nombre -> System.out.println("Nombre: " + nombre));

        System.out.println("----------------------------");

        SequencedCollection<String> nombresSequencedCollection = new ArrayList<>();
        nombresSequencedCollection.addFirst("Ana");
        nombresSequencedCollection.addLast("Pedro");
        nombresSequencedCollection.addLast("Luis");

        nombresSequencedCollection.forEach(nombre -> System.out.println("Nombre: " + nombre));

        System.out.println("----------------------------");

        nombresSequencedCollection.reversed().forEach(nombre -> System.out.println("Nombre: " + nombre));

        System.out.println("----------------------------");

        List<String> frutas = new ArrayList<>();
        frutas.addAll(List.of("Manzana", "Pera", "Uva", "Mango", "Piña"));

        frutas.forEach(fruta -> System.out.println("Fruta: " + fruta));

        System.out.println("----------------------------");
        ArrayList<String> alumnos = new ArrayList<>();

        alumnos.add("Juan");
        alumnos.add("María");
        alumnos.add("Carlos");
        alumnos.stream()
                .filter(alumno -> alumno.startsWith("J"))
                .forEach(alumno -> System.out.println("Alumno: " + alumno));

        System.out.println("----------------------------");
        LinkedList<String> tareas = new LinkedList<>();
        tareas.add("Estudiar Java");
        tareas.add("Practicar Streams");
        tareas.addFirst("Leer teoría");
        tareas.addLast("Hacer ejercicios");

        tareas.stream().forEach(tarea -> System.out.println("Tarea: " + tarea));


        System.out.println("----------------------------");
        Set<Integer> numeros = new HashSet<>();
        numeros.add(10);
        numeros.add(20);
        numeros.add(10);
        numeros.add(40);
        numeros.add(50);

        numeros.forEach(numero -> System.out.println("Número: " + numero));

        System.out.println("----------------------------");
        SequencedSet<String> colores = new LinkedHashSet<>();
        colores.addFirst("Rojo");
        colores.addLast("Azul");
        colores.addLast("Azul");
        colores.addLast("Verde");

        colores.forEach(color -> System.out.println("Color: " + color));
        System.out.println("----------------------------");

        LinkedHashSet<String> ciudades = new LinkedHashSet<>();

        ciudades.add("Lima");
        ciudades.add("Cusco");
        ciudades.add("Arequipa");
        ciudades.add("Arequipa");
        ciudades.addLast("Trujillo");
        ciudades.addFirst("Tacna");

        ciudades.forEach(ciudad -> System.out.println("Ciudad: " + ciudad));

        System.out.println("----------------------------");

        SortedSet<Integer> numerosSet = new TreeSet<>();

        numerosSet.add(50);
        numerosSet.add(10);
        numerosSet.add(30);

        numerosSet.forEach(numero -> System.out.println("Número: " + numero));

        //Las mas utilizadas en BD
        // List - ArrayList
        // Set - HashSet
        // Map - HashMap
        // Queue - PriorityQueue

        // JDBC y JPA utilizan List, Set y Map para manejar colecciones de datos.

        System.out.println("----------------------------");
        NavigableSet<Integer> numerosNav = new TreeSet<>(
                List.of(10, 20, 30, 40, 50)
        );
        System.out.println(numerosNav.lower(30));
        System.out.println(numerosNav.higher(30));
        System.out.println(numerosNav.floor(35));
        System.out.println(numerosNav.ceiling(35));

        System.out.println("----------------------------");


        TreeSet<Integer> edades = new TreeSet<>();

        edades.add(25);
        edades.add(18);
        edades.add(40);

        edades.forEach(edad -> System.out.println("Edad: " + edad));

        System.out.println("----------------------------");

        Queue<String> cola = new LinkedList<>();
        cola.offer("Ana");
        cola.offer("Pedro");
        cola.offer("Luis");

        System.out.println(cola.peek()); //Muestra el primer elemento de la cola sin eliminarlo
        System.out.println(cola.poll()); //Muestra el primer elemento de la cola y lo
        System.out.println(cola);

        System.out.println("----------------------------");
        PriorityQueue<Integer> colaPrio = new PriorityQueue<>();

        colaPrio.offer(50);
        colaPrio.offer(10);
        colaPrio.offer(30);

        System.out.println(colaPrio.poll());
        System.out.println(colaPrio.poll());
        System.out.println(colaPrio.poll());

        System.out.println("----------------------------");

        Deque<String> deque = new ArrayDeque<>();
        deque.addFirst("Ana");
        deque.addLast("Pedro");
        deque.addFirst("Luis");


        deque.stream().forEach(nombre -> System.out.println("Nombre: " + nombre));

        System.out.println("----------------------------");
        ArrayDeque<String> pila = new ArrayDeque<>();

        pila.push("A");
        pila.push("B");
        pila.push("C");

        System.out.println(pila.pop());
        System.out.println(pila.pop());

        System.out.println("----------------------------");

        Map<Integer, String> estudiantes = new HashMap<>();

        estudiantes.put(1, "Juan");
        estudiantes.put(2, "María");
        estudiantes.put(3, "Pedro");
        estudiantes.put(2, "Pepe");

        estudiantes.forEach((id, nombre) -> System.out.println("ID: " + id + ", Nombre: " + nombre));

        System.out.println("----------------------------");

        SequencedMap<Integer, String> mapa = new LinkedHashMap<>();

        mapa.putFirst(1, "Ana");
        mapa.putLast(2, "Pedro");
        mapa.putLast(3, "Luis");

        mapa.forEach((id, nombre) -> System.out.println("ID: " + id + ", Nombre: " + nombre));


        System.out.println("----------------------------");

        SortedMap<Integer, String> personas = new TreeMap<>();

        personas.put(3, "Carlos");
        personas.put(1, "Ana");
        personas.put(2, "Pedro");

        personas.forEach((id, nombre) -> System.out.println("ID: " + id + ", Nombre: " + nombre));

        System.out.println("----------------------------");

        TreeMap<String, Integer> productos = new TreeMap<>();

        productos.put("Zapatillas", 150);
        productos.put("Camisa", 80);
        productos.put("Pantalon", 120);


        productos.forEach((producto, precio) -> System.out.println("Producto: " + producto + ", Precio: " + precio));


    }
}
