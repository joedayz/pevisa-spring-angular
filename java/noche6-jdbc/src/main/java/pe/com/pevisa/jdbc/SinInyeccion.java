package pe.com.pevisa.jdbc;

import java.sql.*;

public class SinInyeccion {

    public static void main(String[] args) throws SQLException {

        String nombre = args.length > 0 ? args[0] : "Té";

        try (Connection con = DriverManager.getConnection("jdbc:h2:mem:tienda")) {
            try (Statement st = con.createStatement()) {
                st.executeUpdate("CREATE TABLE productos (id INTEGER PRIMARY KEY, nombre VARCHAR(40), precio NUMERIC(8,2))");
                st.executeUpdate("INSERT INTO productos VALUES (1, 'Té', 1.99), (2, 'Café', 1.99), (3, 'Torta', 3.99)");
            }

            try (PreparedStatement ps =
                         con.prepareStatement("SELECT id, nombre, precio FROM productos WHERE nombre = ?")) {
                ps.setString(1, nombre);
                try(ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) IO.println("  " + rs.getInt(1) + " " + rs.getString(2) + " " + rs.getBigDecimal(3));
                }
            }

        }
    }
}
