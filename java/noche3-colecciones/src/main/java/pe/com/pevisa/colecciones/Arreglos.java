package pe.com.pevisa.colecciones;

public class Arreglos {

    static void main() {
        String[] nombres = new String[5]; //declarando un arreglo de 5 elementos
        String[] codigos = new String[]{"A", "B", "C", "D", "E"}; //declarando un arreglo de 5 elementos y asignando valores
        String[] apellidos = {"Perez", "Gomez", "Lopez", "Diaz", "Sanchez"}; //declarando un arreglo de 5 elementos y asignando valores

        int[] edades = new int[5]; //declarando un arreglo de 5 elementos
        int[] codigosNumericos = new int[]{1, 2, 3, 4, 5}; //declarando un arreglo de 5 elementos y asignando valores
        int [] salarios = {1000, 2000, 3000, 4000, 5000}; //declarando un arreglo de 5 elementos y asignando valores

        int[][] matriz = new int[3][4]; //declarando una matriz de 3x4
        int[][] matriz2 = new int[][]{
                {1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
                {11, 12, 13, 14},
                {15, 16, 17, 18}}; //declarando una matriz de 3x4 y asignando valores

        int[][] matriz3 = new int[3][]; //declarando una matriz de 3xN, donde N es variable

        matriz3[0] = new int[2];
        matriz3[1] = new int[4];
        matriz3[2] = new int[5];

        for (int i = 0; i < apellidos.length; i++) {
            System.out.println("Apellido: " + apellidos[i]);
        }

        System.out.println("----------------------------");

        for (String apellido : apellidos) {
            System.out.println("Apellido: " + apellido);
        }

        //        int[][] matriz2 = new int[][]{
        //                {1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
        //                {11, 12, 13, 14},
        //                {15, 16, 17, 18}}; //declarando una matriz de 3x4 y asignando valores

        for (int i = 0; i < matriz2.length; i++) {
            for (int j = 0; j < matriz2[i].length; j++) {
                System.out.print(matriz2[i][j] + " ");
            }
            System.out.println();
        }

        //break en arrays
        for (int i = 1; i <=10 ; i++) {
            if (i == 5) {
                break;
            }
            System.out.println(i);
        }

        //continue
        for (int i = 1; i <=10 ; i++) {
            if (i == 5) {
                continue;
            }
            System.out.println(i);
        }
    }
}
