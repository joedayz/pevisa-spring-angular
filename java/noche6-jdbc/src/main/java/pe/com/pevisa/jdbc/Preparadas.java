package pe.com.pevisa.jdbc;

import java.sql.*;
import java.time.LocalDate;

public class Preparadas {

    static void main() throws SQLException {

        try (Connection con = DriverManager.getConnection("jdbc:h2:mem:tienda")) {
            try (Statement st = con.createStatement()) {
                st.executeUpdate("""
                        CREATE TABLE productos (
                            id     INTEGER AUTO_INCREMENT PRIMARY KEY,
                            nombre VARCHAR(40) NOT NULL,
                            precio NUMERIC(8,2),
                            alta   DATE)""");
            }

            String insertar = "INSERT INTO productos (nombre, precio, alta) VALUES (?, ?, ?)";

            try (PreparedStatement ps = con.prepareStatement(insertar, Statement.RETURN_GENERATED_KEYS)) {

                Object[][] filas = {{"Té", "1.99"}, {"Café", "1.99"}, {"Torta", "3.99"}, {"Galleta", "2.99"}, {"Agua", null}};

                for(Object[] f: filas){
                    ps.setString(1, (String) f[0]);

                    if(f[1] == null) ps.setNull(2, Types.NUMERIC);
                    else ps.setBigDecimal(2, new java.math.BigDecimal((String) f[1]));

                    ps.setObject(3, LocalDate.of(2026, 10, 19));
                    ps.executeUpdate();
                    try(ResultSet claves = ps.getGeneratedKeys()){
                        claves.next();
                        IO.println("Insertado " + f[0] + " con id " + claves.getInt(1));
                    }

                }

            }

        }
    }
}
