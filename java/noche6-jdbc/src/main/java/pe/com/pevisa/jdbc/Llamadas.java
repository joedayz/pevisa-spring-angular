package pe.com.pevisa.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Llamadas {

    static void main() throws SQLException {

        try (Connection con = DriverManager.getConnection("jdbc:h2:mem:tienda")) {
            try (Statement st = con.createStatement()) {
                st.executeUpdate("CREATE TABLE productos (id INTEGER PRIMARY KEY, nombre VARCHAR(40), precio NUMERIC(8,2))");
                st.executeUpdate("INSERT INTO productos VALUES (1, 'Té', 1.99), (2, 'Café', 1.99), (3, 'Torta', 3.99)");
                st.executeUpdate("""
                        CREATE ALIAS nombre_dia AS '
                        String nombreDia(java.time.LocalDate fecha) {
                            return fecha.getDayOfWeek().getDisplayName(java.time.format.TextStyle.FULL, java.util.Locale.of("es"));
                        }'""");
                st.executeUpdate("""
                        CREATE ALIAS precio_con_igv AS '
                        java.math.BigDecimal conIgv(java.sql.Connection con, int id) throws java.sql.SQLException {
                            try (var ps = con.prepareStatement("SELECT precio * 1.18 FROM productos WHERE id = ?")) {
                                ps.setInt(1, id);
                                try (var rs = ps.executeQuery()) {
                                    return rs.next() ? rs.getBigDecimal(1).setScale(2, java.math.RoundingMode.HALF_UP) : null;
                                }
                            }
                        }'""");
            }


        }
    }

}
