package pe.com.pevisa.jdbc;

import java.sql.*;

public class Inyeccion {

    static void main(String[] args) throws SQLException {

        String nombre = args.length > 0 ? args[0] : "x' OR '1'='1";

        try (Connection con = DriverManager.getConnection("jdbc:h2:mem:tienda");
             Statement st = con.createStatement()) {
            st.executeUpdate("CREATE TABLE productos (id INTEGER PRIMARY KEY, nombre VARCHAR(40), precio NUMERIC(8,2))");
            st.executeUpdate("INSERT INTO productos VALUES (1, 'Té', 1.99), (2, 'Café', 1.99), (3, 'Torta', 3.99)");

            String sql = "SELECT id, nombre, precio FROM productos WHERE nombre = '" + nombre + "'";

            IO.println("SQL enviado: " + sql);
            try (ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) IO.println("  " + rs.getInt(1) + " " + rs.getString(2) + " " + rs.getBigDecimal(3));
            }
        }

    }
}
