package pe.com.pevisa.control;

import jdk.swing.interop.SwingInterOpUtils;

public class Control {

    static void main() {
        //Tipos, operadores y casting
        int bujias = 45;
        int porCaja = 12;

        System.out.println("Cajas llenas: " + bujias / porCaja);
        System.out.println("Bujias sobrantes: " + bujias % porCaja);

        double precio = 45.90;
        int soles = (int) precio;

        System.out.println("Precio entero en soles: " + soles);

        boolean hayStock = bujias > 0;
        System.out.println(hayStock ? "Hay stock disponible" : "No hay stock disponible");

        byte edad = 25; // -128 a 127
        short cantidad = 30000; // -32,768 a 32,767
        char letra = 0x0041; // Unicode de A //'A'; // 0 a 65,535
        char letraB = 'B'; //"B" no es lo mismo que 0x0042
        int numero = 100000; // -2,147,483,648 a 2,147,483,647
        long numeroGrande = 1234567890123456789L; // -9,223,372,036,854,775,808 a 9,223,372,036,854,775,807

        int resultado = (int) numeroGrande;

        float decimal = 3.14F; // -3.4028235E38 a 3.4028235E38

        //Java tiene tipos primitivos por defecto
        int entero = 10; // int
        double decimalGrande = 10.5; // double

        //String
        String codigo = " ele-017   ";
        System.out.println("Codigo : " + codigo.toString());

        String codigo2 = new String(" ele-017   ");
        System.out.println("Codigo2: " + codigo2);

        String codigo3 = " ele-017   ";
        System.out.println("Codigo3: " + codigo3);


        codigo = codigo.strip().toUpperCase();
        System.out.println("Codigo : " + codigo);
        //System.out.println("Codigo: " + codigo.toLowerCase());

        System.out.println(codigo + " tiene " + codigo.length() + " caracteres");

        System.out.println("Es electrico? " + codigo.startsWith("ELE"));
        System.out.println("Es ELE-017? " + codigo.equals("ELE-017"));

        System.out.println("Codigo: " + codigo.replace("ELE", "ELÉCTRICO"));
        System.out.println("017 es " + codigo.substring(4));
        System.out.println( "  es " + codigo.substring(0, 4) );


        //if / else if
        System.out.println("Estado: " + estado(0, 12));
        System.out.println("Estado: " + estado(8, 12));
        System.out.println("Estado: " + estado(40, 12));


        //switch
        System.out.println("División: " + nombreDivision("GASKETS"));
        System.out.println("División: " + nombreDivision("DENSO"));
        System.out.println("División: " + nombreDivision("BOSCH"));

        //Bucles
        // for, while, do while, break, continue
        int[] stockCallao = { 16, 200, 8, 40};
        System.out.println("Bajos en el Callao: " + contarBajos(stockCallao));

        for (int i = 0; i < stockCallao.length; i++) {
            System.out.println("Posicion " + i + ": " + stockCallao[i]);
        }

        int revisados = 0;
        while(revisados<stockCallao.length){
            if(stockCallao[revisados] == 200){
                System.out.println("La bujia esta en la posicion " + revisados);
                break;
            }
            revisados++; //   revisados = revisados + 1;

        }


    }


    static int contarBajos(int[] stocks){
        int bajos = 0;
        for (int stock: stocks){
            if(stock < 20){
                bajos++;
            }
        }
        return bajos;
    }



    static String estado(int cantidad, int minimo){
        if(cantidad==0){
            return "AGOTADO";
        } else if (cantidad < minimo){
            return "BAJO";
        } else{
            return "OK";
        }
    }

    static String nombreDivision(String codigo){
        return switch (codigo){
            case "GASKETS" -> "Pevisa Gaskets";
            case "DENSO" -> "Denso";
            case "BOSCH" -> "Bosch";
            case "ILUMINACION" -> "Iluminación";
            case "ELECTRICOS" -> "Eléctricos";
            case "RODAMIENTOS" -> "Rodamientos";
            case "SUSPENSION" -> "Suspensión";
            case "LUBRICANTES" -> "Lubricantes y aditivos";
            case "BATERIAS" -> "Baterías";
            case "NEUMATICOS" -> "Neumáticos";
            default -> "División desconocida";
        };

    }




}
