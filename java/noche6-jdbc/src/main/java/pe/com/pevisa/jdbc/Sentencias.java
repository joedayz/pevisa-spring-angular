package pe.com.pevisa.jdbc;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;

public class Sentencias {

    static void main() throws SQLException {

        try (Connection con = DriverManager.getConnection("jdbc:h2:mem:tienda");
             Statement st = con.createStatement()) {

            int n = st.executeUpdate("""
                CREATE TABLE productos (
                    id     INTEGER PRIMARY KEY,
                    nombre VARCHAR(40) NOT NULL,
                    precio NUMERIC(8,2) NOT NULL)""");


            IO.println("CREATE TABLE -> " + n);

            n = st.executeUpdate("""
                INSERT INTO productos VALUES
                    (1, 'Té', 1.99), (2, 'Café', 1.99), (3, 'Torta', 3.99), (4, 'Galleta', 2.99)""");
            IO.println("INSERT -> " + n + " filas");

            // Consulta: executeQuery devuelve un ResultSet
            BigDecimal precio = new BigDecimal("2.50");

            try (ResultSet rs = st.executeQuery("SELECT nombre FROM productos WHERE precio > " + precio)) {
                    while(rs.next()) {
                        IO.println(" Mas caro que " + precio + ": " + rs.getString("nombre"));
                    }
            }

            int id = 4;
            n = st.executeUpdate("UPDATE productos SET precio = " + precio + " WHERE id = " + id);
            IO.println("UPDATE -> " + n + " fila");


            for (String sql : List.of("SELECT COUNT(*) FROM productos", "DELETE FROM productos WHERE id = 3")) {
                boolean esConsulta = st.execute(sql);
                if(esConsulta){
                    try (ResultSet rs = st.getResultSet()) {
                        rs.next();
                        IO.println("Resultado de " + sql + ": " + rs.getInt(1));

                    }
                }else{
                    int filas = st.getUpdateCount();
                    IO.println("Filas afectadas por " + sql + ": " + filas);
                }
            }

        }
    }
}
