

import module java.sql;

import java.math.BigDecimal;
import java.time.LocalDate;

record Producto(int id, String nombre, BigDecimal precio, LocalDate alta) {
}

void main() throws SQLException {

    try (Connection con = DriverManager.getConnection("jdbc:h2:mem:tienda")) {
        try (Statement st = con.createStatement()) {
            st.executeUpdate("""
                    CREATE TABLE productos (
                        id INTEGER PRIMARY KEY, nombre VARCHAR(40), precio NUMERIC(8,2),
                        alta DATE, modificado TIMESTAMP, vendido TIMESTAMP WITH TIME ZONE)""");
            st.executeUpdate("""
                    INSERT INTO productos VALUES
                      (1, 'Té',      1.99, DATE '2026-01-10', TIMESTAMP '2026-10-19 09:30:00', TIMESTAMP WITH TIME ZONE '2026-10-19 09:30:00-05:00'),
                      (2, 'Café',    1.99, DATE '2026-02-15', NULL, NULL),
                      (3, 'Torta',   3.99, DATE '2026-03-20', NULL, NULL),
                      (4, 'Galleta', NULL, DATE '2026-04-25', NULL, NULL)""");
        }

        String consulta = "SELECT id, nombre, precio, alta FROM productos WHERE id > ? ORDER BY id";
        try (PreparedStatement ps = con.prepareStatement(consulta)) {
            ps.setInt(1, 0);
            try (ResultSet rs = ps.executeQuery()) {
                try {
                    rs.getString("nombre");
                } catch (SQLException e) {
                    IO.println("Leer antes de next(): " + e.getMessage().lines().findFirst().orElseThrow());
                }
                var productos = new java.util.ArrayList<Producto>();
                while (rs.next()) {
                    int id = rs.getObject(1, Integer.class);   // o rs.getInt(1): columnas desde 1
                    String nombre = rs.getObject("nombre", String.class);   // o rs.getString("nombre")
                    double precioPrimitivo = rs.getDouble("precio");        // NULL -> 0.0
                    boolean eraNull = rs.wasNull();                         // ...por eso existe wasNull()
                    BigDecimal precio = rs.getBigDecimal("precio");         // NULL -> null
                    LocalDate alta = rs.getObject("alta", LocalDate.class);
                    IO.println("  %d %-8s getDouble=%-5s wasNull=%-5s getBigDecimal=%s".formatted(
                            id, nombre, precioPrimitivo, eraNull, precio));
                    productos.add(new Producto(id, nombre, precio, alta));
                }

                IO.println("¿next() otra vez? " + rs.next());
                IO.println("Lista de records: " + productos.getFirst() + " ... (" + productos.size() + ")");

            }


            // Correspondiencia de tipos SQL <-> java.time (JDBC 4.2)
            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery("SELECT alta, modificado, vendido FROM productos WHERE id = 1")) {
                rs.next();
                LocalDate alta = rs.getObject("alta", LocalDate.class);
                LocalDateTime modificado = rs.getObject("modificado", LocalDateTime.class);
                OffsetDateTime vendido = rs.getObject("vendido", OffsetDateTime.class);
                IO.println("DATE                     -> LocalDate:      " + alta);
                IO.println("TIMESTAMP                -> LocalDateTime:  " + modificado);
                IO.println("TIMESTAMP WITH TIME ZONE -> OffsetDateTime: " + vendido);
                IO.println("getObject(1) sin clase   -> " + rs.getObject(1).getClass().getName());
                IO.println("getDate(1) (API antigua) -> " + rs.getDate(1).getClass().getName());
                try {
                    rs.getString("precio");
                } catch (SQLException e) {
                    IO.println("columna inexistente -> SQLState " + e.getSQLState());
                }
            }


        }

    }


}
